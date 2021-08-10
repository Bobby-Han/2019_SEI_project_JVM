package edu.nju;

import org.apache.commons.cli.*;

import java.util.List;

public class CommandLineUtil {
    private static CommandLine commandLine;
    private static CommandLineParser parser = new DefaultParser();
    private static Options options = new Options();
    private boolean sideEffect;
    public static final String WRONG_MESSAGE = "Invalid input.";
    private List<String> userArg;

    /**
     * you can define options here
     * or you can create a func such as [static void defineOptions()] and call it before parse input
     */
    static {
        Option help = new Option("h","help",false,"print out all predefined options and usage");
        Option print = new Option("p","print",true,"print parameter values");
        Option set = new Option("s",false,"set the sideEffect variable in CommandlineUtil to true");
        options.addOption(help);
        options.addOption(print);
        options.addOption(set);
    }

    /**
     * step1 add some option rules（you can do it in static{}）
     * step2 parse the input
     * step3 handle options
     * @param args input of program
     */
    public void main(String[] args){
        parseInput(args);
        handleOptions();
        /*printHelpMessage();*/



    }


    /**
     * Print the usage of all options
     * Actually, you can print anything to pass the test
     * but you are recommended to use HelpFormatter to see what will happen
     */
    private static void printHelpMessage() {
        String header = "header of help message";
        String footer = "footer of help message";
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("myapp", header, options, footer);


    }

    /**
     * Parse the input and handle exception
     * @param args origin args form input
     */
    public void parseInput(String[] args) {
        try{
            commandLine= parser.parse(options,args);
        }catch (ParseException exp){
            System.err.println(exp.getMessage());
            System.exit(-1);
        }
    }

    /**
     * You can handle options here or create your own func
     */
    public void handleOptions() {
        if(commandLine.hasOption("h")){
            System.out.println("help");
        }else {
            userArg=commandLine.getArgList();
            if(userArg.size()==0){
                System.out.println(WRONG_MESSAGE);
            }else {
                if(commandLine.hasOption("p")){
                    System.out.println(commandLine.getOptionValue("p"));
                }
            }
        }
    }
    public boolean getSideEffectFlag(){
        if(commandLine.hasOption("h")){
            return false;
        }else {
            if(commandLine.hasOption("s")){
                return true;
            }else {
                return false;
            }
        }
    }
}