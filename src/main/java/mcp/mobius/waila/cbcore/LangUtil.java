package mcp.mobius.waila.cbcore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import mcp.mobius.waila.api.BackwardCompatibility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.Language;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Easy localisation access.
 */
@BackwardCompatibility
@Deprecated
public class LangUtil {

    public static LangUtil instance = new LangUtil(null);

    public String prefix;

    public LangUtil(String prefix) {
        this.prefix = prefix;
    }

    /**
     * translate a key with every possible way
     * @deprecated standard localization is enough in most situation nowadays. <br>
     * Also, don't do server side localization with LanguageRegistry. It is bad
     * @see StatCollector#translateToLocal(String)
     */
    public static String translateG(String s, Object... format) {
        return instance.translate(s, format);
    }

    public String translate(String s, Object... format) {
        if (prefix != null && !s.startsWith(prefix + ".")) s = prefix + "." + s;
        String ret = LanguageRegistry.instance().getStringLocalization(s);
        if (ret.isEmpty()) ret = LanguageRegistry.instance().getStringLocalization(s, "en_US");
        if (ret.isEmpty()) ret = StatCollector.translateToLocal(s);
        if (ret.isEmpty()) return s;
        if (format.length > 0) ret = String.format(ret, format);
        return ret;
    }

    public void addLangFile(InputStream resource, String lang) throws IOException {
        LanguageRegistry reg = LanguageRegistry.instance();
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8));
        while (true) {
            String read = reader.readLine();
            if (read == null) break;

            int equalIndex = read.indexOf('=');
            if (equalIndex == -1) continue;
            String key = read.substring(0, equalIndex);
            String value = read.substring(equalIndex + 1);
            if (prefix != null) key = prefix + "." + key;
            reg.addStringLocalization(key, lang, value);
        }
    }

    @SideOnly(Side.CLIENT)
    public static LangUtil loadLangDir(String domain) {
        return new LangUtil(domain).addLangDir(new ResourceLocation(domain, "lang"));
    }

    @SideOnly(Side.CLIENT)
    public LangUtil addLangDir(ResourceLocation dir) {
        IResourceManager resManager = Minecraft.getMinecraft().getResourceManager();
        for (Language lang : Minecraft.getMinecraft().getLanguageManager().getLanguages()) {
            String langID = lang.getLanguageCode();
            IResource langRes;
            try {
                langRes = resManager.getResource(
                        new ResourceLocation(dir.getResourceDomain(), dir.getResourcePath() + '/' + langID + ".lang"));
            } catch (Exception e) {
                continue;
            }
            try {
                addLangFile(langRes.getInputStream(), langID);
            } catch (IOException e) {
                System.err.println("Failed to load lang resource. domain=" + prefix + ", resource=" + langRes);
                e.printStackTrace();
            }
        }

        return this;
    }
}
