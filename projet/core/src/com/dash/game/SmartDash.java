package com.dash.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class SmartDash extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	BitmapFont font;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 1, 1);
		batch.begin();


		CharSequence str = "Hello World!";
		font.draw(batch, "caca", 300, 300);

		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
