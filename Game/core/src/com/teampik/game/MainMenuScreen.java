package com.teampik.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;

public class MainMenuScreen implements Screen{
	
	Skin skin;
	Stage stage;
	
	TextField tf;
	TextField tf2;
	
	MyGdxGame context;
	String txtVal;
	String txtVal2;
	
	public MainMenuScreen(MyGdxGame game){
        this.context = game;
        
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        stage = new Stage();
                      

        
        tf = new TextField("", skin);
        tf2 = new TextField("", skin);
        
        tf.setPosition(0, 0);
        tf.setWidth(200f);
        tf.setHeight(20f);
        
        

        tf.setTextFieldListener(new TextFieldListener() {

        	@Override
        	public void keyTyped(TextField textField, char key) {
        		txtVal= textField.getText();
        	}
        });

        
        tf2.setPosition(Gdx.graphics.getWidth()- 200, 0);
        tf2.setWidth(200f);
        tf2.setHeight(20f);
        
        tf2.setTextFieldListener(new TextFieldListener() {

        	@Override
        	public void keyTyped(TextField textField, char key) {
        		txtVal2= textField.getText();
        	}
        });
        
        stage.addActor(tf);
        stage.addActor(tf2);
        
        
        game.inputMultiplexer.addProcessor(stage);
        
        
}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
        
		
		context.batch.begin();
		//stage.draw();
		context.batch.draw(context.img, 0, 0);
		
		context.batch.end();
		
		context.batch.begin();
		stage.draw();
		
		
		
		
		context.batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
