package com.undeadstudio.quest.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {

	public static final String TAG = Assets.class.getName();
	public static final Assets instance = new Assets();
	public AssetManager assetManager;
	public AssetLevel assetLevel;

	private void Assets() {
	}

	public void init(AssetManager assetManager) {
		this.assetManager = assetManager;
		// set asset manager error handler
		assetManager.setErrorListener(this);
		AssetLevel test = new AssetLevel("test.tmx", assetManager);

	}

	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		String filename = asset.fileName;
		Gdx.app.error(TAG, "Couldn't load asset '" + filename + "'",
				(Exception) throwable);

	}

	@Override
	public void dispose() {
		assetManager.dispose();
	}

	public class AssetLevel {

		public TiledMap map;

		public AssetLevel(String filename, AssetManager assetManager) {

			// only needed once
			assetManager.setLoader(TiledMap.class, new TmxMapLoader(
					new ExternalFileHandleResolver()));
			assetManager.load("My Games/Quest/" + filename, TiledMap.class);
			map = assetManager.get("My Games/Quest/" + filename);

		}

	}

}
