package ao.znt.x_vs_o;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.cocos2d.sound.SoundEngine;

public class MainActivity extends AppCompatActivity {

    private Contador contador;
    Vibrator vibrator;
    Animation animation1;
    private LinearLayout l00,l01,l02,l10,l11,l12,l20,l21,l22;
    char[][] tabuleiro = {
            {'_','_','_'},
            {'_','_','_'},
            {'_','_','_'}
    };
    private char vez = 'x';
    public TextView txtTempoX, txtTempoO;

//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contador = new Contador(8000,1000);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);



        preloadCache();
        primeiroJogador();
        txtTempoX = findViewById(R.id.contador_x);
        txtTempoO = findViewById(R.id.contador_o);


        ImageView peca01 = findViewById(R.id.pecao1);
        peca01.setOnLongClickListener(new MyOnLongClickListener());

        findViewById(R.id.pecao2).setOnLongClickListener(new MyOnLongClickListener());
        findViewById(R.id.pecao3).setOnLongClickListener(new MyOnLongClickListener());
        findViewById(R.id.pecao4).setOnLongClickListener(new MyOnLongClickListener());
        findViewById(R.id.pecao5).setOnLongClickListener(new MyOnLongClickListener());
        //findViewById(R.id.pecao6).setOnLongClickListener(new MyOnLongClickListener());

        findViewById(R.id.pecax1).setOnLongClickListener(new MyOnLongClickListener());
        findViewById(R.id.pecax2).setOnLongClickListener(new MyOnLongClickListener());
        findViewById(R.id.pecax3).setOnLongClickListener(new MyOnLongClickListener());
        findViewById(R.id.pecax4).setOnLongClickListener(new MyOnLongClickListener());
        findViewById(R.id.pecax5).setOnLongClickListener(new MyOnLongClickListener());
        //findViewById(R.id.pecax6).setOnLongClickListener(new MyOnLongClickListener());





      /*findViewById(R.id.img1).setOnLongClickListener(new MyOnLongClickListener());
      findViewById(R.id.img2).setOnLongClickListener(new MyOnLongClickListener());
      findViewById(R.id.img3).setOnLongClickListener(new MyOnLongClickListener());
      findViewById(R.id.img4).setOnLongClickListener(new MyOnLongClickListener());
      findViewById(R.id.img4).setOnLongClickListener(new MyOnLongClickListener());
    */

        findViewById(R.id.frame).setOnDragListener(new MyOnDragListener(1));
        findViewById(R.id.framePecasO).setOnDragListener(new MyOnDragListener(1));
        findViewById(R.id.framePecasX).setOnDragListener(new MyOnDragListener(2));


        l00 = findViewById(R.id.layout00);
        l00.setOnDragListener(new MyOnDragListener(3));
        l01 = findViewById(R.id.layout01);
        l01.setOnDragListener(new MyOnDragListener(4));
        l02 = findViewById(R.id.layout02);
        l02.setOnDragListener(new MyOnDragListener(1));
        l10 = findViewById(R.id.layout10);
        l10.setOnDragListener(new MyOnDragListener(2));
        l11 = findViewById(R.id.layout11);
        l11.setOnDragListener(new MyOnDragListener(3));
        l12 = findViewById(R.id.layout12);
        l12.setOnDragListener(new MyOnDragListener(4));
        l20 = findViewById(R.id.layout20);
        l20.setOnDragListener(new MyOnDragListener(4));
        l21 = findViewById(R.id.layout21);
        l21.setOnDragListener(new MyOnDragListener(4));
        l22 = findViewById(R.id.layout22);
        l22.setOnDragListener(new MyOnDragListener(4));

        animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anime);

    }
    public void preloadCache(){
        //SoundEngine.sharedEngine().preloadEffect(this,R.raw.error);
        SoundEngine.sharedEngine().playEffect(this,R.raw.random);
        SoundEngine.sharedEngine().playEffect(this,R.raw.click);
        SoundEngine.sharedEngine().playEffect(this,R.raw.low);
        SoundEngine.sharedEngine().playEffect(this,R.raw.error_one);
        SoundEngine.sharedEngine().playEffect(this,R.raw.uccess);
    }
    public void primeiroJogador()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final String[] escolha = {"EKS","BOOL"};

        builder.setItems(escolha, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface p1, int p2)
            {
                Toast.makeText(getBaseContext(),"Vencedor: "+escolha[p2],Toast.LENGTH_LONG).show();
                vez = (p2 == 0) ? 'x' : 'o';
            }
        });
        builder.setTitle("PRIMEIRO A JOGAR");
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void vibra(){
        if(Build.VERSION.SDK_INT >= 26){
            vibrator.vibrate(VibrationEffect.createOneShot(200,VibrationEffect.EFFECT_CLICK));
        }else{
            vibrator.vibrate(200);
        }
    }
    class MyOnLongClickListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            Log.d("VIEW",""+v.getTag());
            ClipData data = ClipData.newPlainText("simple_text", "text");
            View.DragShadowBuilder sb;
            if(v.getTag().equals("X"))
                sb = new View.DragShadowBuilder(findViewById(R.id.sombraX));
            else
                sb = new View.DragShadowBuilder(findViewById(R.id.sombraBola));

            v.startDrag(data, sb, v, 0);
            v.setVisibility(View.INVISIBLE);
            return(true);
        }
    }
    public void soundDrag(){
        SoundEngine.sharedEngine().playEffect(this,R.raw.random);
        vibra();
    }
    public void soundDrop(){
        SoundEngine.sharedEngine().playEffect(this,R.raw.click);
        //vibra();
    }
    public void sonWin(){
        SoundEngine.sharedEngine().playEffect(this,R.raw.low);
    }
    public void soundError(){
        SoundEngine.sharedEngine().playEffect(this,R.raw.error_one);
    }
    public void soundSucess(){
        SoundEngine.sharedEngine().playEffect(this,R.raw.uccess);
    }
    class MyOnDragListener implements View.OnDragListener {
        private int num;

        public MyOnDragListener(int num){
            super();
            this.num = num;
        }
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();

            switch(action){
                case DragEvent.ACTION_DRAG_STARTED:
                    soundDrag();
                    Log.i("ACTION", num+" ==> DRAG_STARTED");
                    if(event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)){
                        return(true);
                    }
                    return(false);
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.i("ACTION", num+" ==> DRAG_ENTERED");

                    LinearLayout ctr = (LinearLayout) v;
                    if(ctr.getChildCount()!=0){
                        if(v.getId()==R.id.framePecasX || v.getId()==R.id.framePecasO)
                            v.setBackgroundColor(Color.YELLOW);
                        else if(v.getId()==R.id.frame){
                            // v.setBackgroundColor(Color.WHITE);
                        }
                        else{
                            v.setBackgroundColor(Color.RED);
                        }
                    }
                    else{
                        v.setBackgroundColor(Color.YELLOW);
                    }

                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    Log.i("ACTION", num+" ==> DRAG_LOCATION");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.i("ACTION", num+" ==> DRAG_EXITED");
                    if(v.getTag() != null){
                        v.setBackgroundResource(R.drawable.letter);
                    }else {
                        v.setBackgroundColor(Color.BLUE);
                    }
                    break;
                case DragEvent.ACTION_DROP:
                    Log.i("ACTION", num+" ==> ACTION_DROP");
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();

                    LinearLayout container = (LinearLayout) v;
                    int i = container.getChildCount();
                    Log.d("Filho",""+i);
                    if((container.getChildCount() != 0) || container.getId()==R.id.frame || container.getId()==R.id.framePecasO || container.getId()==R.id.framePecasX || pecaJaUsada(owner)){
                        //verificar se a peca esta a sair do tabulriro
                        soundError();
                        view.setVisibility(View.VISIBLE);
                    }else if(view.getTag().equals("X")&&vez=='x'){//quem deve jogar?
                        soundDrop();

                        contador.cancel();
                        contador.start();

                        owner.removeView(view);
                        container.addView(view);
                        view.setVisibility(View.VISIBLE);

                        //verificar se ha um vencefor
                        preencherTabela(container,view);
                        anima(verificarVencedor(tabuleiro));

                        vez = 'o';
                    }else if(view.getTag().equals("O")&&vez=='o'){//quem deve jogar?
                        soundDrop();

                        contador.cancel();
                        contador.start();

                        owner.removeView(view);
                        container.addView(view);
                        view.setVisibility(View.VISIBLE);
                        //verificar se ha um vencefor
                        preencherTabela(container,view);
                        int valor = verificarVencedor(tabuleiro);
                        anima(verificarVencedor(tabuleiro));
                        vez = 'x';
                    }else{
                        soundError();
                        view.setVisibility(View.VISIBLE);
                    }

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.i("ACTION", num+" ==> DRAG_ENDED");
                    if(v.getTag() != null){
                        if(v.getTag().toString().equals("tabuleiro")){
                            v.setBackgroundResource(R.drawable.letter);
                        }else{
                            v.setBackgroundColor(Color.BLUE);
                        }
                    }
                    break;
            }

            return true;
        }
        private boolean pecaJaUsada(ViewGroup owner)
        {
            switch(owner.getId()){
                case R.id.layout00 : return true;

                case R.id.layout01: return true;

                case R.id.layout02 : return true;

                case R.id.layout10: return true;

                case R.id.layout11 : return true;

                case R.id.layout12: return true;

                case R.id.layout20 : return true;

                case R.id.layout21: return true;

                case R.id.layout22: return true;

                default: return false;
            }

        }
        private void anima(int i){
            if(i==11){
                animaLinha1();
                contador.cancel();
                soundSucess();
                //mostraJanelaVencedor("X");
            }else if(i==-11){
                animaColuna2();
                contador.cancel();
                soundSucess();
                //mostraJanelaVencedor("O");
            }
            else if(i==12){
                animaLinha2();
                contador.cancel();
                soundSucess();
                //mostraJanelaVencedor("X");
            }else if(i==-12){
                animaLinha2();
                contador.cancel();
                soundSucess();
                mostraJanelaVencedor("O");
            }else if(i==13){
                animaLinha3();
                contador.cancel();
                soundSucess();
                //mostraJanelaVencedor("X");
            }else if(i==-13){
                animaLinha3();
                contador.cancel();
                soundSucess();
                //mostraJanelaVencedor("O");
            }else if(i==61){
                animaColuna1();
                contador.cancel();
                soundSucess();
                //mostraJanelaVencedor("X");
            }else if(i==-61){
                animaColuna1();
                contador.cancel();
                soundSucess();
                //mostraJanelaVencedor("O");
            }else if(i==62){
                animaColuna2();
                contador.cancel();
                soundSucess();
                //mostraJanelaVencedor("X");
            }else if(i==-62){
                animaColuna2();
                contador.cancel();
                soundSucess();
               // mostraJanelaVencedor("O");
            }else if(i==63){
                animaColuna3();
                contador.cancel();
                soundSucess();
             //   mostraJanelaVencedor("X");
            }else if(i==-63){
                animaColuna3();
                contador.cancel();
                soundSucess();
                //mostraJanelaVencedor("O");
            }else if(i==44){
                animaDiagonalP();
                contador.cancel();
                soundSucess();
                //mostraJanelaVencedor("X");
            }else if(i==-44){
                animaDiagonalP();
                contador.cancel();
                soundSucess();
                //mostraJanelaVencedor("O");
            }else if(i==40){
                animaDiagonalS();
                contador.cancel();
                soundSucess();
                //mostraJanelaVencedor("X");
            }else if(i==-40){
                animaDiagonalS();
                contador.cancel();
                soundSucess();
                //mostraJanelaVencedor("O");
            }
        }

        private void mostraJanelaVencedor(String p0)
        {
            Toast.makeText(getBaseContext(),"Vencedor: "+p0,Toast.LENGTH_LONG).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("VENCEDOR");
            builder.setMessage("PARABENS "+p0);
            builder.setCancelable(true);
            builder.setPositiveButton("reiniciar", new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface p1, int p2)
                {
                    // TODO: Implement this method
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }


        private void preencherTabela(LinearLayout ly, View view)
        {
            if(R.id.layout00==ly.getId()){
                String tag = (String) l00.getChildAt(0).getTag();
                if(tag.equals("X")){
                    tabuleiro[0][0] = 'x';
                }else if(tag.equals("O")){
                    tabuleiro[0][0] = 'o';
                }

            }else if(R.id.layout01==ly.getId()){
                String tag = (String) l01.getChildAt(0).getTag();
                if(tag.equals("X")){
                    tabuleiro[0][1] = 'x';
                }else if(tag.equals("O")){
                    tabuleiro[0][1] = 'o';
                }

            }else if(R.id.layout02==ly.getId()){
                String tag = (String) l02.getChildAt(0).getTag();
                if(tag.equals("X")){
                    tabuleiro[0][2] = 'x';
                }else if(tag.equals("O")){
                    tabuleiro[0][2] = 'o';
                }

            }else if(R.id.layout10==ly.getId()){
                String tag = (String) l10.getChildAt(0).getTag();
                if(tag.equals("X")){
                    tabuleiro[1][0] = 'x';
                }else if(tag.equals("O")){
                    tabuleiro[1][0] = 'o';
                }

            }else if(R.id.layout11==ly.getId()){
                String tag = (String) l11.getChildAt(0).getTag();
                if(tag.equals("X")){
                    tabuleiro[1][1] = 'x';
                }else if(tag.equals("O")){
                    tabuleiro[1][1] = 'o';
                }

            }else if(R.id.layout12==ly.getId()){
                String tag = (String) l12.getChildAt(0).getTag();
                if(tag.equals("X")){
                    tabuleiro[1][2] = 'x';
                }else if(tag.equals("O")){
                    tabuleiro[1][2] = 'o';
                }

            }else if(R.id.layout20==ly.getId()){
                String tag = (String) l20.getChildAt(0).getTag();
                if(tag.equals("X")){
                    tabuleiro[2][0] = 'x';
                }else if(tag.equals("O")){
                    tabuleiro[2][0] = 'o';
                }

            }else if(R.id.layout21==ly.getId()){
                String tag = (String) l21.getChildAt(0).getTag();
                if(tag.equals("X")){
                    tabuleiro[2][1] = 'x';
                }else if(tag.equals("O")){
                    tabuleiro[2][1] = 'o';
                }

            }else if(R.id.layout22==ly.getId()){
                String tag = (String) l22.getChildAt(0).getTag();
                if(tag.equals("X")){
                    tabuleiro[2][2] = 'x';
                }else if(tag.equals("O")){
                    tabuleiro[2][2] = 'o';
                }

            }

        }
    }
    public void animaLinha1(){
        l00.getChildAt(0).startAnimation(animation1);
        l01.getChildAt(0).startAnimation(animation1);
        l02.getChildAt(0).startAnimation(animation1);
    }public void animaLinha2(){
        l10.getChildAt(0).startAnimation(animation1);
        l11.getChildAt(0).startAnimation(animation1);
        l12.getChildAt(0).startAnimation(animation1);
    }public void animaLinha3(){
        l20.getChildAt(0).startAnimation(animation1);
        l21.getChildAt(0).startAnimation(animation1);
        l22.getChildAt(0).startAnimation(animation1);
    }public void animaColuna1(){
        l00.getChildAt(0).startAnimation(animation1);
        l10.getChildAt(0).startAnimation(animation1);
        l20.getChildAt(0).startAnimation(animation1);
    }public void animaColuna2(){
        l01.getChildAt(0).startAnimation(animation1);
        l11.getChildAt(0).startAnimation(animation1);
        l21.getChildAt(0).startAnimation(animation1);
    }public void animaColuna3(){
        l02.getChildAt(0).startAnimation(animation1);
        l12.getChildAt(0).startAnimation(animation1);
        l22.getChildAt(0).startAnimation(animation1);
    }public void animaDiagonalP(){
        l00.getChildAt(0).startAnimation(animation1);
        l11.getChildAt(0).startAnimation(animation1);
        l22.getChildAt(0).startAnimation(animation1);
    }public void animaDiagonalS(){
        l02.getChildAt(0).startAnimation(animation1);
        l11.getChildAt(0).startAnimation(animation1);
        l20.getChildAt(0).startAnimation(animation1);
    }
    public int verificarVencedor(char b[][])
    {
        // Checking for Rows for X or O victory.
        for (int row = 0; row < 3; row++)
        {
            if (b[row][0] == b[row][1] && b[row][1] == b[row][2])
            {
                if (b[row][0] == 'x'){
                    if(row==0)
                        return 11;//l1
                    else if(row==1)
                        return 12;
                    else if(row==2)
                        return 13;
                    //return +10;
                }
                else if (b[row][0] == 'o'){
                    if(row==0)
                        return -11;//l1
                    else if(row==1)
                        return -12;
                    else if(row==2)
                        return -13;
                    //return -10;
                }
            }
        }

        // Checking for Columns for X or O victory.
        for (int col = 0; col < 3; col++)
        {
            if (b[0][col] == b[1][col] && b[1][col] == b[2][col])
            {
                if (b[0][col] == 'x'){
                    if(col==0)
                        return 61;//l1
                    else if(col==1)
                        return 62;
                    else if(col==2)
                        return 63;
                    //return +10;
                }
                else if (b[0][col] == 'o'){
                    if(col==0)
                        return -61;//l1
                    else if(col==1)
                        return -62;
                    else if(col==2)
                        return -63;
                    //return -10;
                }
            }
        }

        // Checking for Diagonals for X or O victory.
        if (b[0][0] == b[1][1] && b[1][1] == b[2][2])
        {
            if (b[0][0] == 'x'){
                return 44;
            }
            else if (b[0][0] == 'o'){
                return -44;
            }
        }
        if (b[0][2] == b[1][1] && b[1][1] == b[2][0])
        {
            if (b[0][2] == 'x'){
                return 40;
            }
            else if (b[0][2] == 'o'){
                return -40;
            }
        }

        // Else if none of them have won then return 0
        return 0;

    }

    public class Contador extends CountDownTimer
    {

        public Contador(long time, long interval){
            super(time,interval);
        }

        @Override
        public void onTick(long p1)
        {
            int progress = (int) (p1/1000);
            if(vez=='o'){

                txtTempoO.setText(Integer.toString(progress));

            }else{
                txtTempoX.setText(Integer.toString(progress));
            }
        }

        @Override
        public void onFinish()
        {
            if(vez=='x'){
                txtTempoX.setText("EKS PERDEU");
            }else{
                txtTempoO.setText("BOOL PERDEU");
            }
        }


    }

}
