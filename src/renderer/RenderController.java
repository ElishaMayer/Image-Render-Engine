package renderer;

import scene.Scene;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class RenderController {
    private Boolean[] _threadsFinish;
    private double[] _progress ;
    private ImageWriter _imageWriter;
    private Scene _scene;
    private boolean _grid = false;
    private LocalDateTime _start;

    public RenderController( ImageWriter imageWriter, Scene scene) {
        _imageWriter = imageWriter;
        _scene = scene;
    }

    public RenderController( ImageWriter imageWriter, Scene scene,boolean grid) {
        _imageWriter = imageWriter;
        _scene = scene;
        _grid=grid;
    }


    public void renderImage(){
        _scene.buildBoxes();
        _start =  LocalDateTime.now();
        int _cores = Runtime.getRuntime().availableProcessors();
        int seg = _imageWriter.getNy()/ _cores +1;

        _threadsFinish = new Boolean[_cores];
        _progress = new double[_cores];
        for(int i = 0; i< _cores; i++){
            Render rn = new Render(i,this,_imageWriter,_scene,0,_imageWriter.getNx(),i*seg,Math.min(i*seg+seg,_imageWriter.getNy()));
            Thread thread = new Thread(rn);
            thread.start();
            _threadsFinish[i]=false;
        }
    }

    protected void progress(double prog,int id){
        _progress[id]=prog;
        double sum = 0;
        for(double p :_progress)
            sum+=p;
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        System.out.print("\r"+df.format(sum)+"%");
        System.out.flush();
    }

    protected void finish(int id){
        _threadsFinish[id]=true;
        if(Arrays.stream(_threadsFinish).allMatch(val -> val)) {
            if(_grid)
                new Render(_imageWriter,_scene).printGrid(50);
            _imageWriter.writeToimage();
            LocalDateTime end = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            System.out.println("\rStart time: "+dtf.format(_start));
            System.out.println("End time: "+dtf.format(end));

        }
    }
}
