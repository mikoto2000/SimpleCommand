package jp.dip.oyasirazu.simplecommand.command;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 外部コマンドクラス
 * TODO: コマンドのタイムアウト時間を指定できるようにする
 * TODO: 標準エラー処理の扱いを決める
 * TODO: 標準出力・エラー出力が多いとロックするらしいのでそのあたりを調べる
 */
public class Command {
    
    /**
     * コマンド文字列
     */
    private String command;

    /**
     * コマンド引数
     */
    private String[] args;
    /**
     * コマンドを指定し、インスタンス化
     * @param command コマンド
     */
    public Command(String command) {
        this.command = command;
    }
    
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String... args) {
        this.args = args;
    }

    public String execute() throws CommandException {
        return execute(null);
    }
    
    /**
     * コマンドを実行し、標準出力を返す。
     * @param stdIn 標準入力
     * @return 実行コマンドの標準出力
     * @throws CommandException コマンド実行時に問題が発生した場合
     */
    public String execute(String stdIn) throws CommandException {
        // コマンドの組み立て
        List<String> command = buildCommand(args);
        
        // プロセスの作成
        ProcessBuilder pb = new ProcessBuilder(command);
        Process process;
        try {
            process = pb.start();
        } catch (IOException e) {
            throw new CommandException(e);
        }

        // 標準入力があれば入れる
        if (stdIn != null) {
            inputStdIn(process, stdIn);
        }

        // 標準出力文字列を返す
        return readStdOutStr(process);
    }
    
    /**
     * コマンドと、引数配列から、CommandBuilder に渡すリストを組み立てる。
     * @param args コマンドに渡す引数
     * @return コマンドと引数が格納されたリスト
     */
    private List<String> buildCommand(String[] args) {
        ArrayList<String> command = new ArrayList<String>();
        command.add(getCommand());
        
        // 引数追加
        if (args != null) {
            for (String arg : args) {
                command.add(arg);
            }
        }
        
        return command;
    }

    /**
     * Process の標準入力に入力する。
     * TODO: ユーティリティとして外だしするか、 process を Command クラスが持つようにする。
     * 
     * @param process プロセス
     * @param stdIn 入力文字列
     * @throws CommandException コマンド実行時に問題が発生した場合
     */
    private void inputStdIn(Process process, String stdIn) throws CommandException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
        try {
            bw.write(stdIn);
            bw.close();
        } catch (IOException e) {
            throw new CommandException(e);
        }
    }

    /**
     * 標準出力文字列を取得する。
     * TODO: ユーティリティとして外だしするか、 process を Command クラスが持つようにする。
     * 
     * @param process プロセス
     * @return 標準出力文字列
     * @throws CommandException コマンド実行時に問題が発生した場合
     */
    private static String readStdOutStr(Process process) throws CommandException {
        return readStreamStr(process.getInputStream());
    }
    
    /**
     * inputStream から受け取った文字列を取得する。
     * TODO: ユーティリティとして外だしする。
     * 
     * @param inputStream InputStream
     * @return inputStream から受け取った文字列
     * @throws CommandException
     */
    private static String readStreamStr(InputStream inputStream) throws CommandException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        
        StringBuilder sb = new StringBuilder();
        try {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            return(sb.toString());
        } catch (IOException e) {
            throw new CommandException(e);
        }
    }
}
