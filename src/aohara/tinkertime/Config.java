package aohara.tinkertime;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import aohara.common.AbstractConfig;
import aohara.tinkertime.models.Mod;

/**
 * Stores and Retrieves User Configuration Data.
 * 
 * Holds data related to Mod Zip File Storage and Mod Installation Directory.
 * 
 * @author Andrew O'Hara
 */
public class Config extends AbstractConfig {
	
	private static final String KSP_PATH = "kspPath";
	
	public Config(){
		super(TinkerTime.NAME);
		setLoadOnGet(true);
	}
	
	public void setExecutablePath(Path path) throws IllegalPathException {
		if (path != null && path.toFile().isFile()){
			setProperty(KSP_PATH, path.toString());
		} else {
			throw new IllegalPathException("Please select your KSP executable");
		}
	}
	public Path getGameDataPath(){
		verifyConfig();
		return Paths.get(getProperty(KSP_PATH)).getParent().resolve("GameData");
	}
	
	public Path getKspPath(){
		if (hasProperty(KSP_PATH)){
			return Paths.get(getProperty(KSP_PATH));
		}
		return null;
	}

	public Path getModZipPath(Mod mod){
		// TODO: Move to Mod Class (give it reference to config)
		return getModsPath().resolve(mod.getNewestFileName());
	}
	
	public Path getModImagePath(Mod mod){
		// TODO: move to Mod Class (give it reference to config)
		Path imageName = Paths.get(mod.getPageUrl().getFile()).getFileName();
		return getFolder().resolve("imageCache").resolve(imageName);
	}
	
	public Path getModsPath(){
		Path path = getFolder().resolve("mods");
		path.toFile().mkdirs();
		return path;
	}
	
	@Override
	public void setProperty(String key, String value){
		super.setProperty(key, value);
		save();
	}
	
	@SuppressWarnings("serial")
	public class IllegalPathException extends Exception {
		private IllegalPathException(String message){
			super(message);
		}
	}
	
	protected static void verifyConfig(){
		Config config = new Config();
		if (config.getKspPath() == null){
			updateConfig(false, true);
		}
	}
	
	public static void updateConfig(boolean restartOnSuccess, boolean exitOnCancel){
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Please select the path to the KSP executable");
		chooser.setApproveButtonText("Select KSP Path");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int result = chooser.showSaveDialog(null);
		
		if (result == JFileChooser.APPROVE_OPTION){
			try {
				new Config().setExecutablePath(chooser.getSelectedFile().toPath());
			} catch (IllegalPathException e) {
				JOptionPane.showMessageDialog(null, e.toString());
				updateConfig(restartOnSuccess, exitOnCancel);
			}
		} else {
			System.exit(0);
		}
		
		if (restartOnSuccess){
			JOptionPane.showMessageDialog(null, "Restart required");
			System.exit(0);
		}
	}

}