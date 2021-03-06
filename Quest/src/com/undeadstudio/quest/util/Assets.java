package com.undeadstudio.quest.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {

	public static final String TAG = Assets.class.getName();
	public static final Assets instance = new Assets();
	public AssetManager assetManager;

	public AssetFonts fonts;
	public AssetFloor floor;
	public AssetWall wall;
	public AssetDoor door;
	public AssetCorridor corridor;
	public AssetChest chest;
	public AssetStairsUp stairsUp;
	public AssetStairsDown stairsDown;
	public AssetMonster monster;
	public AssetPlayer player;

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

		fonts = new AssetFonts();

		chest = new AssetChest(atlas);
		floor = new AssetFloor(atlas);
		wall = new AssetWall(atlas);
		door = new AssetDoor(atlas);
		corridor = new AssetCorridor(atlas);
		stairsUp = new AssetStairsUp(atlas);
		stairsDown = new AssetStairsDown(atlas);
		monster = new AssetMonster(atlas);
		player = new AssetPlayer(atlas);

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
		fonts.defaultSmall.dispose();
		fonts.defaultNormal.dispose();
		fonts.defaultBig.dispose();
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

	public class AssetFloor {
		public final AtlasRegion reg;

		public AssetFloor(TextureAtlas atlas) {
			reg = atlas.findRegion("floor");
		}
	}

	public class AssetWall {
		public final AtlasRegion reg;

		public AssetWall(TextureAtlas atlas) {
			reg = atlas.findRegion("wall");
		}
	}

	public class AssetDoor {
		public final AtlasRegion reg;

		public AssetDoor(TextureAtlas atlas) {
			reg = atlas.findRegion("door");
		}
	}

	public class AssetCorridor {
		public final AtlasRegion reg;

		public AssetCorridor(TextureAtlas atlas) {
			reg = atlas.findRegion("corridor");
		}
	}

	public class AssetChest {
		public final AtlasRegion reg;
		public final AtlasRegion open;

		public AssetChest(TextureAtlas atlas) {
			reg = atlas.findRegion("chest");
			open = atlas.findRegion("open");
		}
	}

	public class AssetStairsUp {
		public final AtlasRegion reg;

		public AssetStairsUp(TextureAtlas atlas) {
			reg = atlas.findRegion("stairsup");
		}
	}

	public class AssetStairsDown {
		public final AtlasRegion reg;

		public AssetStairsDown(TextureAtlas atlas) {
			reg = atlas.findRegion("stairsdown");
		}
	}

	public class AssetMonster {
		public final AtlasRegion reg;

		public AssetMonster(TextureAtlas atlas) {
			reg = atlas.findRegion("monster");
		}

	}

	public class AssetPlayer {
		public final AtlasRegion reg;

		public AssetPlayer(TextureAtlas atlas) {
			reg = atlas.findRegion("player");
		}
	}

}
