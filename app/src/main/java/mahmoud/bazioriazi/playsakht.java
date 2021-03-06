package mahmoud.bazioriazi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;


public class playsakht extends Activity {
    String[] backcolors=new String[]{"#FFCDD2","#E040FB","#512DA8"
            ,"#673AB7","#1976D2","#2196F3","#0288D1","#03A9F4"
            ,"#0097A7","#00BCD4","#B2EBF2","#00796B","#009688"
            ,"#AFB42B","#CDDC39","#FFEB3B"
            ,"#FFA000","#FFC107","#F57C00","#FF9800","#FF5722"
            ,"#607D8B"};

    TextView tvadad1;  // adade aval
    TextView tvadad2;	// adade 2vom
    TextView tvadad3;	// adade 3vom
    TextView textviewplusmines;	// +
    TextView equal;	// =
    int adad;
    boolean bool;
    boolean opbool;// baraye +  ya  -
    public int[] myrandomnumbers=new int[2];
    public int result;
    public int count=0;
    private ProgressBar customProgress;
    public int myProgress=0;
    public CountDownTimer mCountDownTimer;
    public int counter=1; // baraye inke az dafeye dovom b bad progress bar kar konad
    public int etmam=1; // baraye inke 2 bar methode payan() seda zade nashavad
    public int hasel;
    public SharedPreferences pref;
    public SharedPreferences.Editor editor;
    public MediaPlayer mp_sakht;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playsakht);
        mp_sakht= MediaPlayer.create(this, R.raw.backgroundvoice);
        if(MainActivity.tmp_bool){

                // yani vaghti yek bar dg ro mizanim dar safeye result

                mp_sakht.setLooping(true);
                mp_sakht.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp_sakht.start();
                    }
                });
                mp_sakht.start();

        }

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/DANSTEVIS.OTF");
        RelativeLayout rl = (RelativeLayout)findViewById(R.id.relativelayout);
        tvadad1=(TextView) findViewById(R.id.textView1);
        tvadad2=(TextView) findViewById(R.id.textView2);
        tvadad3=(TextView) findViewById(R.id.textView3);
        textviewplusmines=(TextView) findViewById(R.id.textviewplusmines);
        equal=(TextView) findViewById(R.id.textView5);
        // set fonts
        tvadad1.setTypeface(custom_font);
        tvadad2.setTypeface(custom_font);
        tvadad3.setTypeface(custom_font);
        textviewplusmines.setTypeface(custom_font);
        equal.setTypeface(custom_font);
        customProgress =(ProgressBar)findViewById(R.id.customProgress);

        pref=getApplicationContext().getSharedPreferences("MyPref_sakht", MODE_PRIVATE);
        editor=pref.edit();
        editor.clear();

        // baraye range backgroud
        int min = 0;
        int max = 21;
        Random backcolorrandom = new Random();
        int i1 = backcolorrandom.nextInt(max - min + 1) + min;
        rl.setBackgroundColor(Color.parseColor(backcolors[i1]));
        trueorfalse();
    }

    protected void trueorfalse(){

        bool=booleanrandom();
        if(bool==true) maketrue_ebarat();
        else           makefalse_ebarat();
    }

    public void maketrue_ebarat(){

        myrandomnumbers[0]=random();
        myrandomnumbers[1]=random();

        // bataye sakhte + ya -  age true bargardoond + va age false bargardoond -  hesab mikonim
        opbool =booleanrandom();
        if(opbool){
            // operation =    '  +  '
            textviewplusmines.setText("+");
            result=myrandomnumbers[0]+myrandomnumbers[1];
        }
        else if (!opbool){

            // operation =    '  -  '

            textviewplusmines.setText("-");
            result=myrandomnumbers[0]-myrandomnumbers[1];
        }

        tvadad1.setText(String.valueOf(myrandomnumbers[0]));
        tvadad2.setText(String.valueOf(myrandomnumbers[1]));
        tvadad3.setText(""+result);
    }


    public void makefalse_ebarat(){

        myrandomnumbers[0]=random();
        myrandomnumbers[1]=random();

        opbool =booleanrandom();
        if(opbool) {
            // operation =    '  +  '
            textviewplusmines.setText("+");
            hasel = myrandomnumbers[0] + myrandomnumbers[1];
        }
        else if(!opbool){
            // operation =    '  -  '
            textviewplusmines.setText("-");
            hasel = myrandomnumbers[0] - myrandomnumbers[1];
        }

        int tmp;

        Random random=new Random();
        tmp=random.nextInt(100);

        if(tmp<30){
            // adade result kamtar az jame adaad bashad
            int x=hasel-2;
            result=random.nextInt(hasel-x)+x;
        }
        else{
            // adade result bisgtar az jame adaad bashad
            int x=hasel+3;
            result=random.nextInt(x-hasel)+hasel;
        }
        // set the int array to text views
        tvadad1.setText(String.valueOf(myrandomnumbers[0]));
        tvadad2.setText(String.valueOf(myrandomnumbers[1]));
        tvadad3.setText(""+result);
    }

    // BUTTON TRUE
    public void btntrue(View view){
        try{
            mCountDownTimer.cancel();
            customProgress.setProgress(0);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        finally{
            if(textviewplusmines.getText().toString()=="+")
            {
                if(result==myrandomnumbers[0]+myrandomnumbers[1]){

                    count++;
                    // moghayese behtarin result ba natijeye hale hazer
                    int saved_best_result=pref.getInt("best_result_sakht",0);
                    if(count>=saved_best_result){
                        editor.putInt("best_result_sakht",count);
                        editor.commit();
                    }
                    // continue
                    trueorfalse();
                    myprogressbar();
                }
                else {
                    if(etmam==1){
                        payan();
                        etmam=2;
                    }

                }
            }

            else if(textviewplusmines.getText().toString()=="-")
            {
                if(result==myrandomnumbers[0]-myrandomnumbers[1]){

                    count++;
                    // moghayese behtarin result ba natijeye hale hazer
                    int saved_best_result=pref.getInt("best_result_sakht",0);
                    if(count>=saved_best_result){
                        editor.putInt("best_result_sakht",count);
                        editor.commit();
                    }
                    // continue
                    trueorfalse();
                    myprogressbar();
                }
                else {
                    if(etmam==1){
                        payan();
                        etmam=2;
                    }

                }
            }

        }

    }

    // BUTTON FALSE
    public void btnfalse(View view){


        try{
            mCountDownTimer.cancel();
            customProgress.setProgress(0);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(textviewplusmines.getText().toString()=="+"){
                if(result!=myrandomnumbers[0]+myrandomnumbers[1]){
                    count++;
                    // moghayese behtarin result ba natijeye hale hazer
                    int saved_best_result=pref.getInt("best_result_sakht",0);
                    if(count>=saved_best_result){
                        editor.putInt("best_result_sakht",count);
                        editor.commit();
                    }
                    //continue
                    trueorfalse();
                    myprogressbar();
                }
                else{
                    if(etmam==1){
                        payan();
                        etmam=2;
                    }
                }

            }
            else if(textviewplusmines.getText().toString()=="-"){
                if(result!=myrandomnumbers[0]-myrandomnumbers[1]){
                    count++;
                    // moghayese behtarin result ba natijeye hale hazer
                    int saved_best_result=pref.getInt("best_result_sakht",0);
                    if(count>=saved_best_result){
                        editor.putInt("best_result_sakht",count);
                        editor.commit();
                    }
                    //continue
                    trueorfalse();
                    myprogressbar();
                }
                else{
                    if(etmam==1){
                        payan();
                        etmam=2;
                    }
                }
            }

        }

    }

    public void payan(){
        if(MainActivity.sound){
            if(mp_sakht.isPlaying()){
                mp_sakht.stop();
                mp_sakht.reset();
                mp_sakht.release();
                mp_sakht=null;
            }

        }

        // namayesh emtiaz va az sar giri
        int bestresult=pref.getInt("best_result_sakht", 0);
        String bestresult_string=String.valueOf(bestresult);

        String emtiaz=String.valueOf(count);
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(500);
        // mire b activitie result va natijeha ra ha mibarad

        Intent result_intent = new Intent(getApplicationContext(),Result.class);
        Bundle extras = new Bundle();
        extras.putString("emtiaz", emtiaz);
        extras.putString("best_emtiaz", bestresult_string);
        extras.putString("level","sakht");
        result_intent.putExtras(extras);
        startActivity(result_intent);
        overridePendingTransition(R.anim.left, R.anim.abc_fade_out);
        onDestroy();
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }


    public int random(){

        Random random = new Random();
        adad = random.nextInt(10 - 1) + 1;
        return adad;
    }

    public boolean booleanrandom(){
        Random random = new Random();
        adad = random.nextInt(100);
        if(adad<50)
            return true;
        else
            return false;
    }

    public void myprogressbar(){
        myProgress=0;
        mCountDownTimer=new CountDownTimer(1000,40) {

            @Override
            public void onTick(long millisUntilFinished) {
                myProgress++;
                customProgress.setProgress(myProgress);
            }

            @Override
            public void onFinish() {

                //Toast.makeText(getApplicationContext(), "finished", 1000).show();
                if(etmam==1) {
                    payan();
                    etmam=2;
                }
            }
        };
        mCountDownTimer.start();


    }

    @Override
    public void onBackPressed() {

        if (count==0) {
            if(MainActivity.sound){
                if(mp_sakht.isPlaying()){
                    mp_sakht.stop();
                    mp_sakht.reset();
                    mp_sakht.release();
                    mp_sakht=null;
                }
            }

            super.onBackPressed();
            onDestroy();
            Intent myintent=new Intent(playsakht.this,MainActivity.class);
            startActivity(myintent);
            overridePendingTransition(R.anim.left, R.anim.abc_fade_out);
        }
    }

}

// vaghti user ham eshtebah miznad va ham inke timer tamoom mishe , nabayad jofteshoon  payan() ro seda bezanan

