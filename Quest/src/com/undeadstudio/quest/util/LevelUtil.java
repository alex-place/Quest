package com.undeadstudio.quest.util;

import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.undeadstudio.quest.map.Level.BLOCK_TYPE;

public class LevelUtil {

	public static String TAG = LevelUtil.class.getName();

	public static int[][] convertPixmap(String filename) {
		Pixmap pixmap = new Pixmap(Gdx.files.internal(filename));
		int[][] map = new int[pixmap.getWidth()][pixmap.getHeight()];

		for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++) {
			for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++) {
				// get color of current pixel as 32-bit RGBA value
				int currentPixel = pixmap.getPixel(pixelX, pixelY);
				if (BLOCK_TYPE.EMPTY.sameColor(currentPixel)) {
					map[pixelX][pixelY] = Constants.BLOCKTYPE_EMPTY;
				} else if (BLOCK_TYPE.ROCK.sameColor(currentPixel)) {
					map[pixelX][pixelY] = Constants.BLOCKTYPE_FLOOR;
				}

				else {
					map[pixelX][pixelY] = Constants.BLOCKTYPE_EMPTY;
					Gdx.app.error(
							TAG,
							"Unidentified color "
									+ pixmap.getPixel(pixelX, pixelY)
									+ " found at " + pixelX + "," + pixelY);
				}
			}
		}

		return map;

	}

	public static int[][] convertTextFile(String filename) {
		String file = null;
		int[][] result = null;

		if (Gdx.files.external(filename).exists()) {
			file = Gdx.files.external(filename).readString();
		} else {
			Gdx.app.error(TAG, filename + " does not exist!");
		}

		if (file != null) {
			System.out.println(file);

			int width = 0;
			int height = 0;

			Scanner scanner = new Scanner(file);

			width = scanner.nextLine().split(" ").length;
			height = width;

			result = new int[width][height];

			System.out.println(width + " " + height);

			scanner.close();

			scanner = new Scanner(file);

			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					try {
						if (scanner.hasNext())
							result[x][y] = scanner.nextInt();
						System.out.print(result[x][y]);

					} catch (Exception e) {
						System.out.println(result[x][y]);
						e.printStackTrace();
					}
				}
				System.out.println();
			}

			scanner.close();

		}
		return result;

	}
}
