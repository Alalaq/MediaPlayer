import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import java.io.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class MediaPlayerApp extends AbstractMediaPlayerApp{ //TODO make possibility to choose the instrument
    private final static int DURATION = 500;
    private MediaPlayer mp;
    private static int volume;
    private static int octave;
    private String command;
    private int[] notes;
    private String[] notesName;
    private Map<String, Integer> mapNotes;
    private Scanner sc;

    public static void main(String[] args) {
        MediaPlayerApp mpApp = new MediaPlayerApp();
    }

    @Override
    protected void initialize() {
        notes = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11}; //notes from C to B
        notesName = new String[]{"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
        mapNotes = new HashMap<>();
        sc = new Scanner(System.in);
        octave = 5;
        volume = 40;
        mp = new MediaPlayer();
    }

    @Override
    protected void start() {
        System.out.println("Welcome to the Media Player! Please enter the wanted octave: ");
        octave = sc.nextInt(); //TODO exception that octave can be only between 1 and 7
        changingOctave();
        fillMap();

        System.out.println("Please enter also wanted volume: ");
        volume = sc.nextInt();

        System.out.println("Here's notes you can use: ");
        for (String elem : notesName){
            System.out.print(elem + "  ");
        }

        try (DataOutputStream out = new DataOutputStream(new FileOutputStream("C:\\Users\\muzik\\Desktop\\product.txt"));
             DataInputStream in = new DataInputStream(new FileInputStream("C:\\Users\\muzik\\Desktop\\product.txt"))) {


        do {
            System.out.println("\nWrite note to play, FILE to play from file all played notes or EXIT to exit the program");
            command = sc.nextLine().trim().toUpperCase(Locale.ROOT);

            if (!command.equals("FILE")) {
                //TODO make possibility to change the instrument, volume or octave during the process
                mp.playSound(0, DURATION, volume, mapNotes.get(command));
                out.writeInt(mapNotes.get(command));
            }
            else{
                int a;
                while (((a = in.read())) != -1){
                    if (a != 0) {
                        mp.playSound(0, DURATION, volume, a);
                    }
                }
            }
        }
        while (!command.equals("exit"));

        System.out.println("Closing the program...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changingOctave(){
        for (int i = 0; i < notes.length; i++){
            notes[i] += octave * 12;
        }
    }

    private void fillMap(){
        for (int i = 0; i < notes.length; i++){
            mapNotes.put(notesName[i], notes[i]);
        }
    }
}
