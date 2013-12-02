package com.undeadstudio.quest.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {

	public static final String TAG = Assets.class.getName();
	public static final Assets instance = new Assets();
	public AssetManager assetManager;
	public Texture playerTexture;

	public AssetSpriteSheet spriteSheet;
	public AssetTiledMap map;

	private void Assets() {
	}

	public void init(AssetManager assetManager) {
		this.assetManager = assetManager;
		// set asset manager error handler
		assetManager.setErrorListener(this);
		spriteSheet = new AssetSpriteSheet();

		playerTexture = new Texture(
				Gdx.files.internal("data/testcharacter.png"));

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
		playerTexture.dispose();
	}

	public class AssetSpriteSheet {

		Texture tex;
		TextureRegion[][] reg;

		public AssetSpriteSheet() {

		}

		public TextureRegion[][] getRegions(String filename) {
			tex = new Texture(Gdx.files.internal(filename));
			reg = TextureRegion.split(tex, 32, 32);
			return reg;
		}

	}

	public class AssetTiledMap {

		TiledMap map;

		public AssetTiledMap(String filename) {

		}

		public TiledMap getMap(String filename) {
			return new TmxMapLoader().load("levels/test.tmx");
		}

	}

}
