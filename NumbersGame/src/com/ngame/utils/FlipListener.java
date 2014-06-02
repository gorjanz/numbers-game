package com.ngame.utils;

import android.content.Context;
import android.widget.Toast;
import fr.castorflex.android.flipimageview.library.FlipImageView;

public class FlipListener implements FlipImageView.OnFlipListener {

	private Context ctx;
	
	public FlipListener(Context ctx) {
		this.ctx = ctx;
	}

	@Override
	public void onClick(FlipImageView view) {
		// TODO Auto-generated method stub
		Toast.makeText(ctx, "onFlipClick", Toast.LENGTH_SHORT).show();	
	}

	@Override
	public void onFlipStart(FlipImageView view) {
		// TODO Auto-generated method stub
		Toast.makeText(ctx, "onFlipStart", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onFlipEnd(FlipImageView view) {
		// TODO Auto-generated method stub
		Toast.makeText(ctx, "onFlipEnd", Toast.LENGTH_SHORT).show();
		
	}

}
