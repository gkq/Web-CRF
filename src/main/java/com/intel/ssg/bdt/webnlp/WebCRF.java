package com.intel.ssg.bdt.webnlp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import com.intel.ssg.bdt.nlp.*;

public class WebCRF
        implements Runnable
{
    public static CRFModel modelBme;
    public static CRFModel modelPos;

    static {
        String pathBme = "resource/modelBme";
        String pathPos = "resource/modelPos";
        modelPos = CRFModel.loadBinaryFile(pathPos);
	System.out.println("modelPos has been loaded");
        StringBuffer content = new StringBuffer();
        try {
            BufferedReader buf = new BufferedReader(new FileReader(pathBme));
            String tmp;
            while ((tmp = buf.readLine()) != null) {
                content.append(tmp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        modelBme = CRFModel.deSerializer(content.toString());
	System.out.println("modelBme has been loaded");
    }

    private final Socket m_socket;
    private final int m_num;


    WebCRF(Socket socket, int num )
    {
        m_socket = socket;
        m_num = num;

        Thread handler = new Thread( this, "handler-" + m_num );
        handler.start();
    }

    public void run()
    {
        try
        {
            try
            {
                System.out.println( m_num + " Connected." );
                BufferedReader in = new BufferedReader( new InputStreamReader( m_socket.getInputStream() ) );
                OutputStreamWriter out = new OutputStreamWriter( m_socket.getOutputStream() );
//                out.write( "Welcome connection #" + m_num + "\n\r" );
//                out.flush();

                while ( true )
                {
		    String content = in.readLine();
//		    String line = null;
//		    while((line = in.readLine())!=null){
//			content +=line;
//		    }
                    if ( content == "" )
                    {
                        System.out.println( m_num + " Closed." );
                        return;
                    }
                    else
                    {
                        System.out.println( m_num + " Read: " + content);
                        if ( content.equals( "exit" ))
                        {
                            System.out.println( m_num + " Closing Connection." );
                            return;
                        }
                        else if( content.equals(""))
                        {
                            System.out.println("please input test data!");
                        }
                        //else if ( line.equals( "crash" ) )
                        //{
                        //    System.out.println( m_num + " Simulating a crash of the Server..." );
                        //    Runtime.getRuntime().halt(0);
                        //}中文总
                        else
                        {
			    long startTime = System.currentTimeMillis();
                            System.out.println( m_num + " Write: echo " + content );
			    String[] testData = content.split("。");
			    for(int i=0;i<testData.length;i++){
				testData[i] += "。";
			    }
                            System.out.println( m_num + "after process:" + testData[0] );
                            String[] tmp = WordTag.PosTag(testData, WebCRF.modelBme, modelPos);
                            String res = "";
                            for(int i=0; i< tmp.length; i++){
                                res += tmp[i] + "\n";
                            }
                            out.write( res+ "\n\r" ); //
                            out.flush();
			    long duration = System.currentTimeMillis() - startTime;
			    System.out.println("Process duration(ms):" + duration);
                            System.out.println( m_num + "after analysis:" + res );
                        }
                    }
                }
            }
            finally
            {
                m_socket.close();
            }
        }
        catch ( IOException e )
        {
            System.out.println( m_num + " Error: " + e.toString() );
        }
    }

    public static void main( String[] args )
            throws Exception
    {
        int port = 9090;
        if ( args.length > 0 )
        {
            port = Integer.parseInt( args[0] );
        }
        System.out.println( "Accepting connections on port: " + port );
        int nextNum = 1;
        ServerSocket serverSocket = new ServerSocket( port );
        while ( true )
        {
            Socket socket = serverSocket.accept();
            WebCRF hw = new WebCRF( socket, nextNum++ );
        }
    }
}
