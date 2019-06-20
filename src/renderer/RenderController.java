package renderer;

import scene.Scene;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RenderController {
    private Boolean[] _threadsFinish;
    private double[] _progress ;
    private ImageWriter _imageWriter;
    private Scene _scene;
    private boolean _grid = false;
    private LocalDateTime _start;
    private ExecutorService _pool;


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
        _pool = Executors.newFixedThreadPool(_cores);

        _threadsFinish = new Boolean[_imageWriter.getNx()-1];
        _progress = new double[_imageWriter.getNx()-1];
        for(int i = 0; i< _imageWriter.getNx()-1; i++){
            Render rn = new Render(i,this,_imageWriter,_scene,i,i+1,0,_imageWriter.getNy());
            _pool.execute(rn);
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
            _pool.shutdownNow();
        }
    }
}
