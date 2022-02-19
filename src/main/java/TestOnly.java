
import aquality.selenium.browser.AqualityServices;
import aquality.selenium.configuration.BrowserProfile;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.automation.testing.utility.Settings;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Set;

public class TestOnly {

    public static void main(String[] args) throws IOException {

        Settings settings = Settings.getInstance();

        System.out.println(settings.getObjectsPath());
        System.out.println(settings.getSetting("/hello/world"));

    }

}
