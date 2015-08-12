/**
 * Custom dialog builder class.
 * @author Jayesh A Tembhekar
 */

package  com.ezetap.android.utils;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CustomDialog extends Dialog {
	
	//Constants
	public static final int ALERT_DIALOG = 0x27856;
	public static final int PROGRESS_DIALOG = 0x27878;
	public static final int OPTIONS_DIALOG = 0x27889;
		
	//Views
	private TextView mTitle = null;
	private TextView mMessage = null;
	private TextView mTime = null;
	private Button mOptionButton = null;
	private View mProgressbarView = null;
	private ImageView mDismissButton = null;
	private ImageView mSeparator = null;
	private TextView mProgressbarMessage = null;
	private ProgressBar mProgressbarSpinner = null;
	
	//Other
	private Context mContext = null;
	private Resources mResources = null;
	private String mPackageName = null;
	private boolean mIsParameterSet = false;
	private KeyEventObserver mKeyEventObserver = null;
	
	
	/**
	 * Constructor for class <strong>CustomDialog</strong>,
	 * must be used to set particular theme.
	 * @param context
	 * @param theme
	 */
	public CustomDialog(Context context,int theme) {
		super(context,theme);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mContext = context;
		initDialogLayout();
	}
	
	/**
	 * Constructor for class <strong>CustomDialog</strong>,
	 * to get dialogue with default theme.
	 * @param context
	 */
	public CustomDialog(Context context) {
		super(context,context.getResources().getIdentifier("Theme_Dialog_Translucent", "style", context.getPackageName())); 
		mContext = context;
		mResources = context.getResources();
		mPackageName = context.getPackageName();
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		initDialogLayout();
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DialogInterface.OnKeyListener keyListener = new DialogInterface.OnKeyListener(){
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if(mKeyEventObserver != null)
					mKeyEventObserver.onKey(keyCode);
				if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP){
					return false;
				}
				return true;
			}
			
		};
		this.setOnKeyListener(keyListener);
	}
	
	
	/**
	 * To initialize Custom Dialog's view.
	 */
	private void initDialogLayout(){
		this.setContentView(mResources.getIdentifier("custom_dialog_layout", "layout", mPackageName));
		mTitle = (TextView)findViewById(mResources.getIdentifier("alert_title", "id", mPackageName));
        mMessage = (TextView)findViewById(mResources.getIdentifier("alert_message", "id", mPackageName));
        mTime = (TextView)findViewById(mResources.getIdentifier("alert_time", "id", mPackageName));
        mOptionButton = (Button)findViewById(mResources.getIdentifier("alert_button", "id", mPackageName));
        mDismissButton = (ImageView)findViewById(mResources.getIdentifier("alert_dismiss_button", "id", mPackageName));
        mProgressbarView = findViewById(mResources.getIdentifier("alert_progressbar", "id", mPackageName));
        mProgressbarMessage = (TextView)findViewById(mResources.getIdentifier("Alert_progress_message", "id", mPackageName));
        mSeparator = (ImageView)findViewById(mResources.getIdentifier("alert_title_separator", "id", mPackageName));
        mProgressbarSpinner = (ProgressBar)findViewById(mResources.getIdentifier("alert_progress_spinner", "id", mPackageName));
        
        
        
	}
	
	/**
	 * Set the gravity of message TextView using {@link android.view.Gravity}
	 * constants.
	 * 
	 */
	public void setMessageAlignment(int gravity){
		mMessage.setGravity(gravity);
	}
	
	/**
	 * Method must be called after calling a <strong>Constructor</strong> of 
	 * class <strong>CustomDialog</strong> to set parameter for required 
	 * Dialog box.
	 * @param dialogType
	 * @param title
	 * @param message
	 * @param time
	 */
	public void setDialogParameter(int dialogType,CharSequence title,CharSequence message,CharSequence time){
		mIsParameterSet = true;
		switch(dialogType){
		case ALERT_DIALOG:
			{
				mMessage.setVisibility(View.VISIBLE);
				
				if(title!=null && !title.toString().equals("")){
					mTitle.setVisibility(View.VISIBLE);
					mSeparator.setVisibility(View.VISIBLE);
					mTitle.setText(title);
				}
				if(message!=null){
					mMessage.setText(message);
				}
				if(time!=null && !time.toString().equals("")){
					mTime.setVisibility(View.VISIBLE);
					mTime.setText(time);
				}
			}
			break;
		case OPTIONS_DIALOG:
			{		
				mMessage.setVisibility(View.VISIBLE);
				
				if(title!=null && !title.toString().equals("")){
					mTitle.setVisibility(View.VISIBLE);
					mSeparator.setVisibility(View.VISIBLE);
					mTitle.setText(title);
				}
				if(message!=null){
					mMessage.setText(message);
				}
			}
			break;
		case PROGRESS_DIALOG:
			{
				mProgressbarView.setVisibility(View.VISIBLE);
				if(title != null){
					mTitle.setVisibility(View.VISIBLE);
					mSeparator.setVisibility(View.VISIBLE);
					mTitle.setText(title);
				}
				if(message!=null){
					mProgressbarMessage.setText(message);
				}
			}
			break;
		}
	}
	
	
	/**
	 * Method must be called if parameter set for Options Dialog box
	 * to perform particular task on event of clicking option button.   
	 * @param buttonTitle
	 */
	public void setOptionButton(CharSequence buttonTitle,final Object object,
									final OptionButtonOnClickListner option){
		if(mIsParameterSet==false)
			return;
		mOptionButton.setVisibility(View.VISIBLE);
		if(buttonTitle!=null){
			mOptionButton.setText(buttonTitle);
		}	
		
		mOptionButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				option.onClickOptionButton(CustomDialog.this, object);
				
			}
		});
	}
	
	
	/**
	 * Method must be called if parameter set for Options Dialog box or Alert Dialog box
	 * to perform particular task on event of clicking dismiss button.
	 */
	public void setDismissButton(final DismissButtonOnClickListner dismiss,final Object object){
		if(mIsParameterSet==false)
			return;
		mDismissButton.setVisibility(View.VISIBLE);
		
		mDismissButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				dismiss.onClickDismissButton(CustomDialog.this,object);
				
			}
		});
	}
	
	/**
	 * Set Key Event Observer.
	 * @param observer
	 */
	public void setKeyEventObserver(KeyEventObserver observer){
		mKeyEventObserver = observer;
	}
	
	
	/**
	 * To set dialog box title.
	 * @param title
	 */
	public void setDialogTitle(CharSequence title){
		if(mIsParameterSet==false)
			return;
		mTitle.setVisibility(View.VISIBLE);
		if(title!=null){
			mTitle.setText(title);
		}
	}
	
	
	/**
	 * To set dialog box message contents.
	 * @param message
	 * @param dialogType
	 */
	public void setDialogMessageContents(CharSequence message, int dialogType){
		if(mIsParameterSet==false)
			return;
		if(dialogType==PROGRESS_DIALOG){
			mProgressbarView.setVisibility(View.VISIBLE);
			if(message!=null)
					mProgressbarMessage.setText(message);
		}else {
			mMessage.setVisibility(View.VISIBLE);
			if(message!=null)
					mMessage.setText(message);
		}
	}

	
	/**
	 * To set Progress Dialog box message contents .
	 * @param message
	 */
	public void setTaskProgressCompletion(CharSequence message){
		if(mIsParameterSet==false)
			return;
		mMessage.setVisibility(View.GONE);
		mProgressbarView.setVisibility(View.VISIBLE);
		mProgressbarSpinner.setVisibility(View.GONE);
		if(message!=null)
				mProgressbarMessage.setText(message);
	}
	
	
	
	
	@Override
	public void dismiss() {
		super.dismiss();	
	}

	@Override
	public void cancel() {
		super.cancel();
	}


	
	/**
	 * 
	 * 
	 */
	


	/**
	 * Interface to listen onClick events on "Dismiss" button.
	 */
	public interface DismissButtonOnClickListner{
		/**
		 * onClick events dispatcher for "Dismiss" button.
		 * @param object to pass any object else <strong>null</strong>
		 *
		 */
		public void onClickDismissButton(CustomDialog dialog, Object object);
	}
	
	
	/**
	 * Interface to listen onClick events on "Option" button.   
	 */
	public interface  OptionButtonOnClickListner{
		/**
		 * onClick events dispatcher for "Option" button.   
		 * @param object to pass any object else <strong>null</strong>
		 */
		public void onClickOptionButton(CustomDialog dialog, Object object);
	}
	
	/**
	 * Obsrves Key Events on the Dialog.
	 * @author Shashi Kiran G M
	 *
	 */
	public interface KeyEventObserver{
		public void onKey(int keyCode);
	}
}
