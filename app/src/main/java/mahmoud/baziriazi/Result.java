package mahmoud.baziriazi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class Result extends Activity {
    public String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView tv = (TextView) findViewById(R.id.textView1);
        TextView tv2 = (TextView) findViewById(R.id.textView4);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String Result = extras.getString("emtiaz");
            tv.setText(Result);

            String Best_Result = extras.getString("best_emtiaz");
            tv2.setText(Best_Result);

            level=extras.getString("level");
        }


    }

    public void replay(View view) {
        Intent replay_intent;
        switch (level){
            case "asan" :
                replay_intent  = new Intent(Result.this, playasan.class);
                startActivity(replay_intent);
                break;
            case "motevaset":
                replay_intent = new Intent(Result.this, Playmotevaset.class);
                startActivity(replay_intent);
                break;
            case  "sakht":
                replay_intent = new Intent(Result.this, playsakht.class);
                startActivity(replay_intent);
                break;

        }

    }

    public void goto_menu(View v) {

        Intent exit_intent = new Intent(Result.this, MainActivity.class);
        startActivity(exit_intent);

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent intent = new Intent(Result.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
