package com.undeadstudio.quest.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {

	public static final String TAG = Assets.class.getName();
	public static final Assets instance = new Assets();
	public AssetManager assetManager;
	public Texture playerTexture;

	public AssetSpriteSheet spriteSheet;
	public AssetFonts fonts;
	public AssetRock rock;

	private void Assets() {
	}

	public void init(AssetManager assetManager) {
		this.assetManager = assetManager;
		// set asset manager error handler
		assetManager.setErrorListener(this);
		assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
		// start loading assets and wait until finished
		assetManager.finishLoading();

		Gdx.app.debug(TAG,
				"# of assets loaded: " + assetManager.getAssetNames().size);
		for (String a : assetManager.getAssetNames()) {
			Gdx.app.debug(TAG, "asset: " + a);
		}

		TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);

		spriteSheet = new AssetSpriteSheet();
		fonts = new AssetFonts();
		rock = new AssetRock(atlas);

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
		fonts.defaultSmall.dispose();
		fonts.defaultNormal.dispose();
		fonts.defaultBig.dispose();
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

	public class AssetFonts {
		public final BitmapFont defaultSmall;
		public final BitmapFont defaultNormal;
		public final BitmapFont defaultBig;

		public AssetFonts() {
			// create three fonts using Libgdx's 15px bitmap font
			defaultSmall = new BitmapFont(
					Gdx.files.internal("fonts/arial-15.fnt"), true);
			defaultNormal = new BitmapFont(
					Gdx.files.internal("fonts/arial-15.fnt"), true);
			defaultBig = new BitmapFont(
					Gdx.files.internal("fonts/arial-15.fnt"), true);
			// set font sizes
			defaultSmall.setScale(0.75f);
			defaultNormal.setScale(1.0f);
			defaultBig.setScale(2.0f);
			// enable linear texture filtering for smooth fonts
			defaultSmall.getRegion().getTexture()
					.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultNormal.getRegion().getTexture()
					.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultBig.getRegion().getTexture()
					.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
	}

	public class AssetRock {
		public final AtlasRegion reg;

		public AssetRock(TextureAtlas atlas) {
			reg = atlas.findRegion("rock");
		}
	}

}
