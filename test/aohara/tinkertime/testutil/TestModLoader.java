package aohara.tinkertime.testutil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

import aohara.common.tree.TreeNode;
import aohara.tinkertime.crawlers.Crawler;
import aohara.tinkertime.crawlers.Crawler.Asset;
import aohara.tinkertime.crawlers.CrawlerFactory.UnsupportedHostException;
import aohara.tinkertime.models.ModStructure;
import aohara.tinkertime.testutil.MockHelper.MockMod;

public class TestModLoader {
	
	/**
	 * Load a mod with the given ModStub.
	 * 
	 * Requires the html or json to be in the testRes html or json folders.
	 * 
	 * If there are multiple assets available in the latest release, will select
	 * the first one.
	 * 
	 * @param stub
	 * @return
	 * @throws UnsupportedHostException
	 */
	public static MockMod loadMod(ModStubs stub, boolean fallback) throws UnsupportedHostException {
		return loadCrawlerAndMod(stub, fallback).mod;
	}
	
	public static CrawlerAndMod loadCrawlerAndMod(ModStubs stub, boolean fallback) throws UnsupportedHostException{
		try {
			Crawler<?> crawler = MockHelper.newCrawlerFactory().getCrawler(stub.url, fallback);
			crawler.setAssetSelector(new StaticAssetSelector());
			return new CrawlerAndMod(crawler, new MockMod(crawler.createMod()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static class CrawlerAndMod {
		
		public final Crawler<?> crawler;
		public final MockMod mod;
		
		private CrawlerAndMod(Crawler<?> crawler, MockMod mod){
			this.crawler = crawler;
			this.mod = mod;
		}
	}
	
	public static MockMod loadMod(ModStubs stub) throws UnsupportedHostException{
		return loadMod(stub, false);
	}
	
	private static URL getZipUrl(String modName){
		return TestModLoader.class.getClassLoader().getResource(
			String.format("zips/%s.zip", modName)
		);
	}
	
	public static Path getZipPath(String modName){
		try {
			return Paths.get(getZipUrl(modName).toURI());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static ModStructure getStructure(ModStubs stub) throws IOException{
		return ModStructure.inspectArchive(getZipPath(stub.name));
	}
	
	public static TreeNode getModule(ModStructure struct, String moduleName){
		for (TreeNode module : struct.getModules()){
			if (module.getName().equals(moduleName)){
				return module;
			}
		}
		return null;
	}
	
	private static class StaticAssetSelector implements Crawler.AssetSelector {

		@Override
		public Asset selectAsset(String modName, Collection<Asset> assets) {
			return new ArrayList<>(assets).get(0);
		}
		
	}
}
