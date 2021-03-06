package aohara.tinkertime;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import aohara.common.OS;
import aohara.common.config.Config;
import aohara.common.config.ConfigBuilder;
import aohara.common.config.Constraint.InvalidInputException;
import aohara.common.config.OptionsWindow;

/**
 * Stores and Retrieves User Configuration Data.
 * 
 * @author Andrew O'Hara
 */
public class TinkerConfig {
	 
	private static final String
		GAMEDATA_PATH = "GameData Path",
		AUTO_CHECK_FOR_MOD_UPDATES = "Check for Mod Updates on Startup",
		NUM_CONCURRENT_DOWNLOADS = "Number of Concurrent Downloads",
		KSP_WIN_LAUNCH_ARGS = "KSP Launch Arguments",
		WIN_64 = "win64",
		STARTUP_CHECK_MM_UPDATES = "Check for App Updates on Startup";
		
	private final Config config;
	
	protected TinkerConfig(Config config){
		this.config = config;
	}
	
	public static TinkerConfig create(){
		ConfigBuilder builder = new ConfigBuilder();
		builder.addBooleanProperty(AUTO_CHECK_FOR_MOD_UPDATES, false, false, false);
		builder.addBooleanProperty(STARTUP_CHECK_MM_UPDATES, true, false, false);
		builder.addPathProperty(GAMEDATA_PATH, JFileChooser.DIRECTORIES_ONLY, null, false, false);
		builder.addIntProperty(NUM_CONCURRENT_DOWNLOADS, 4, 1, null, false, false);
		builder.addStringProperty(KSP_WIN_LAUNCH_ARGS, null, true, false);
		
		builder.addStringProperty(WIN_64, null, true, true);
		
		Config config = builder.createConfigInDocuments(
			String.format("%s Config", TinkerTime.SAFE_NAME),
			TinkerTime.NAME,
			"TinkerTime-Options.json"
		);
		
		return new TinkerConfig(config);
	}
	
	// -- Getters -------------------------------------------------------
	
	public Path getGameDataPath(){
		return config.getProperty(GAMEDATA_PATH).getValueAsFile().toPath();
	}
	
	private Path getModCachePath(){
		return getSubFolder(getGameDataPath().getParent(), TinkerTime.SAFE_NAME);	
	}
	
	public Path getModsZipPath(){
		return getSubFolder(getModCachePath(), "modCache");
	}
	
	public Path getImageCachePath(){
		return getSubFolder(getModCachePath(), "imageCache");
	}
	
	public Path getModsListPath(){
		return getModCachePath().resolve("TinkerTime-mods.json");
	}
	
	private Path getSubFolder(Path parent, String subFolder){
		Path path = parent.resolve(subFolder);
		path.toFile().mkdir();
		return path;
	}
	
	public boolean autoCheckForModUpdates(){
		return config.getProperty(AUTO_CHECK_FOR_MOD_UPDATES).getValueAsBool();
	}
	
	public int numConcurrentDownloads(){
		return config.getProperty(NUM_CONCURRENT_DOWNLOADS).getValueAsInt();
	}
	
	public boolean use64BitGame() throws IOException{
		switch(OS.getOs()){
		case Windows:
			// If Windows, only run 64-bit if user chooses to.  Cache user's choice.
			boolean is64Bit = System.getenv("ProgramFiles(x86)") != null; 
			if (is64Bit){
				if (config.getProperty(WIN_64).getValue() == null){
					Boolean use64 = JOptionPane.showConfirmDialog(
						null,
						"Tinker Time has detected that you are runing a 64-bit system.\n" +
						"Would you like to run the 64-bit version of KSP?\n\n" +
						"Caution: The 64-bit Unity Engine for Windows is still unstable.",
						"Launch 64-bit KSP?",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE
					) == JOptionPane.YES_OPTION;
					try {
						config.setProperty(WIN_64, use64);
						config.save();
					} catch (InvalidInputException e) {
						throw new IOException(e);
					}
					return use64;
				} else {
					return config.getProperty(WIN_64).getValueAsBool();
				}
			} else {
				return false;
			}
		case Linux:
			// If Linux, run 64-bit if system is 64-bit
			try(
				BufferedReader r = new BufferedReader(new InputStreamReader(
					Runtime.getRuntime().exec("uname -m").getInputStream()
				))
			){
				return r.readLine().toLowerCase().equals("x86_64");
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		case Osx:
			// If OSX, always run 64-bit
			return true;
		default:
			throw new IllegalStateException();
		}
	}
	
	public String getLaunchArguments(){
		return config.getProperty(KSP_WIN_LAUNCH_ARGS).toString();
	}
	
	public boolean isCheckForMMUpdatesOnStartup(){
		return config.getProperty(STARTUP_CHECK_MM_UPDATES).getValueAsBool();
	}
	
	// -- Verification ----------------------------------------------------
	
	public void updateConfig(){
		new OptionsWindow(config).toDialog();
	}
}
