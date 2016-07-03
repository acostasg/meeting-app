
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import edu.uoc.prac.*;


/**
 * Main program, used to read and execute commands.<br />
 *
 * @author OOP teaching staff
 * @version 1.0
 */
public class Main {

    private static final String COMMENT_LINE = "#";
    private static final String NL = System.getProperty("line.separator");
    private static final String SCREEN = "SCREEN";
    private static final String KEYBOARD = "KEYBOARD";
    private static final String WRONG_COMMAND = "WRONG_COMMAND" + NL;
    private static final String COMMAND_NOT_FOUND = "COMMAND_NOT_FOUND" + NL;

    // Commands:

    private static final String ADD_USER = "addUser";
    private static final String LIST_USERS = "listUsers";
    private static final String ADD_MEETING_GROUP= "addMeetingGroup";
    private static final String ADD_COORGANIZER="addCoorganizer";
    private static final String ADD_MEMBER="addMember";
    private static final String LIST_ALL="listAll";
    private static final String ADD_INTEREST="addInterest";
    private static final String SEARCH_MEETING="searchMeeting";
    private static final String ADD_PLACE="addPlace";
    private static final String ASSIGN_PLACE_MG="assignPlaceMG";
    private static final String ADD_MEETING_MG="addMeetingMG";
    private static final String ADD_ANSWER="addAnswer";
    private static final String LIST_WAITING="listMeetingAnswers";



    private static final String SET_CURRENT_DATE = "setCurrentDate";


    private BufferedReader in;
    private PrintWriter out;

    /**
     * @param in
     *            the BufferedReader to set
     */
    private void setIn(BufferedReader in) {
        this.in = in;
    }

    /**
     * @param in
     *            the BufferedReader to set
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     */
    private void setIn(String in) throws UnsupportedEncodingException,
            FileNotFoundException {
        this.setIn(this.buildReader(in));
    }

    /**
     * @param out
     *            the PrintWriter to set
     */
    private void setOut(PrintWriter out) {
        this.out = out;
    }

    /**
     * @param out
     *            the PrintWriter to set
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     */
    private void setOut(String out) throws UnsupportedEncodingException,
            FileNotFoundException {
        this.setOut(this.buildWriter(out));
    }

    /**
     * Builds the output stream.
     *
     * @param fileName
     *            the output filename or 'SCREEN'
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     */
    private PrintWriter buildWriter(String fileName)
            throws UnsupportedEncodingException, FileNotFoundException {
        PrintWriter out = null;
        if (fileName.equals(SCREEN)) {
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter( System.out, "UTF-8")), true);
        } else {
            File f;
            f = new File(fileName);

            PrintStream stream = new PrintStream (new FileOutputStream(f.getAbsolutePath(), false));

            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(stream, "UTF-8")));
        }
        return out;
    }

    /**
     * Builds the input stream.
     *
     * @param fileName
     *            the input stream filename or "KEYBOARD"
     * @return BufferedReader the in stream
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     */
    private BufferedReader buildReader(String fileName)
            throws UnsupportedEncodingException, FileNotFoundException {
        BufferedReader in = null;
        if (fileName.equals(KEYBOARD)) {
            in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        } else {
            File f = new File(fileName);
            in = new BufferedReader(new InputStreamReader(
                    new FileInputStream(f), "UTF-8"));
        }
        return in;
    }

    /**
     * Finalize. Let's ensure the streams have been closed
     *
     * @see java.lang.Object#finalize()
     */
    @Override
    protected void finalize() throws Throwable {
        // finalization: ensure the streams are closed
        if (this.getOut() != null) {
            this.getOut().close(); // not exception
        }
        if (this.getIn() != null) {
            this.getIn().close(); // ioexception
        }
        // not necessary if extending Object.
        super.finalize();
    }

    /**
     * Input stream getter.
     *
     * @return the input stream
     */
    private BufferedReader getIn() {
        return (this.in);
    }

    /**
     * Output stream getter.
     *
     * @return the output stream
     */
    private PrintWriter getOut() {
        return (this.out);
    }

    /**
     * Program entry point.
     *
     * @param args
     *            arguments to the program
     */
    public static void main(String[] args) {
        Main p = null;
        MeetingManager mm = new MeetingManager();
        if (args.length == 2) {
            p = new Main();
            try {
                p.setOut(args[1]);
                p.setIn(args[0]);
                p.treatInput(mm);
            } catch (Exception e) {
                e.printStackTrace(System.err);
            } finally {
                if (p.getOut() != null) {
                    p.getOut().close(); // not exception
                }
                if (p.getIn() != null) {
                    try {
                        p.getIn().close(); // ioexception
                    } catch (IOException e) {
                        e.printStackTrace(System.err);
                    }
                }
            }
        } else {
            System.err.println("Number of parameters incorrect, syntax is:"
                    + NL + "\t>java Main in out");
            System.err.println("where \tin  is " + KEYBOARD + " or InputFile");
            System.err.println("and \tout is " + SCREEN + "   or OutputFile");
            System.exit(-1);
        }
    }

    /**
     * Treats the input and outputs the results.
     *
     * @param cl
     *            client instance on which to delegate
     * @throws Exception
     *             In case of non I/O error
     */
    public void treatInput(MeetingManager mm) throws Exception {
        String currentLine;
        int line = 0;
        boolean end = false;
        while (!end) {
            try {
                currentLine = in.readLine();
                line++;
                if ((currentLine != null)) {
                    processCommand(mm, currentLine);
                } else {
                    end = true;
                }
            } catch (IOException ex) {
                ex.printStackTrace(System.err);
            }
        }
    }

    private void feedback(MeetingGroup mg){
        if (mg != null) {
            Assignment prova=mg.getAssignment();
            Organizer org=prova.getOrganizer();
            String email=org.getEmail();
            this.getOut().println(" New MeetingGroup "+ mg.getName()+" Coordinator email: "+email);
        }
    }

    /**
     * Processes the line command, delegating on 'tm'.
     *
     * @param cl
     *            Client instance on which to delegate
     * @param currentLine
     *            the current line to process
     * @throws Exception
     */
    private void processCommand(MeetingManager mm, String currentLine)
            throws Exception {

        String line = currentLine.trim();
        if (line.length() == 0) {
            line = line + ",PHANTOM";
        } else if ("(,)".indexOf(line.charAt(line.length() - 1)) == -1) {
            line = line + ",PHANTOM";
        } else {
            line = line + "PHANTOM";
        }
        String[] st = line.split("\\(|,|\\)", -1);

        st = Arrays.copyOf(st, st.length - 1);

        for (int i = 0; i < st.length; i++) {
            st[i] = st[i].trim();
        }
        if (st[0].startsWith(COMMENT_LINE) || st[0].trim().equals("")) {
            this.getOut().println(currentLine);
        }
        else {
            this.getOut().println("## COMMAND ##: " + currentLine);
            try {

                //
                // ADD_USER
                //
                if (st[0].equalsIgnoreCase(Main.ADD_USER)) {
                    if (st.length == 3) {

                        User u=mm.addUser(st[1], st[2]);

                        // write feedback
                        if (u != null) {
                            this.getOut().println("+ New user " + u.getEmail() + " pwd:" + ((User)u).getPassword());
                        }
                    } else {
                        this.getOut().println(Main.WRONG_COMMAND+" addUser(email,pwd)");
                    }
                }
                else if (st[0].equalsIgnoreCase(Main.LIST_USERS)) {
                    if (st.length == 1) {

                        String us =mm.listUsers();

                        // write feedback
                        this.getOut().println(us);

                    } else {
                        this.getOut().println(Main.WRONG_COMMAND+" listUsers");
                    }
                }

                else if (st[0].equalsIgnoreCase(Main.ADD_MEETING_GROUP)) {
                    if (st.length == 5) {

                        MeetingGroup mg =mm.addMeetingGroup(st[1],st[2],st[3],st[4]);
                        if (mg != null) {
                            this.getOut().println(mg);
                        }
                    } else {
                        this.getOut().println(Main.WRONG_COMMAND+ "addMeetingGroup(name,email,pwd,phone)");
                    }
                }

                else if (st[0].equalsIgnoreCase(Main.ADD_COORGANIZER)) {
                    if (st.length == 4) {

                        MeetingGroup mg =mm.addCoorganizer(st[1],st[2],st[3]);

                        // write feedback
                        feedback(mg);
                    } else {
                        this.getOut().println(Main.WRONG_COMMAND+" addCoorganizer(meetingGroup,email,pwd)");
                    }
                }
                else if (st[0].equalsIgnoreCase(Main.ADD_MEMBER)) {
                    if (st.length == 4) {

                        MeetingGroup mg =mm.addMember(st[1],st[2],st[3]);

                        // write feedback
                        feedback(mg);
                    } else {
                        this.getOut().println(Main.WRONG_COMMAND+ " addMember(meetingGroup,email,pwd)");
                    }
                }
                else if (st[0].equalsIgnoreCase(Main.LIST_ALL)) {
                    if (st.length == 2) {

                        MeetingGroup us =mm.listAll(st[1]);

                        // write feedback
                        if (us != null) {
                            this.getOut().println(us);
                        } else {
                            this.getOut().println(Main.WRONG_COMMAND+" listAll(nameMeetingGroup)");
                        }
                    }

                }
                else if (st[0].equalsIgnoreCase(Main.ADD_INTEREST)) {
                    if (st.length == 4) {

                        User us =mm.addInterest(st[1],st[2],st[3]);

                        // write feedback
                        if (us != null) {
                            this.getOut().println(us);
                        } else {
                            this.getOut().println(Main.WRONG_COMMAND+" addInterest(email,pwd,interest)");
                        }
                    }

                }
                if (st[0].equalsIgnoreCase(Main.SEARCH_MEETING)) {
                    if (st.length == 3) {

                        mm.searchMeeting(st[1], st[2]);


                    } else {
                        this.getOut().println(Main.WRONG_COMMAND+" searchMeeting(email,pwd)");
                    }
                }
                if (st[0].equalsIgnoreCase(Main.ADD_PLACE)) {
                    if (st.length == 6) {

                        Place u=mm.addPlace(st[1], st[2], st[3], st[4], st[5]);
                        // write feedback
                        if (u != null) {
                            this.getOut().println("+ New Place " + u);
                        }
                    } else {
                        this.getOut().println(Main.WRONG_COMMAND+" addPlace(name,address,zone,yes/no,country)");
                    }
                }

                if (st[0].equalsIgnoreCase(Main.ASSIGN_PLACE_MG)) {
                    if (st.length == 3) {

                        MeetingGroup u=mm.assignPlaceMG(st[1], st[2]);
                        // write feedback
                        if (u != null) {
                            this.getOut().println("+ Added Place to Meeting Group " + u);
                        }
                    } else {
                        this.getOut().println(Main.WRONG_COMMAND+" assignPlaceMG(idPlace,nameMG)");
                    }
                }

                if (st[0].equalsIgnoreCase(Main.ADD_MEETING_MG)) {
                    if (st.length == 9) {

                        MeetingGroup u=mm.addMeetingMG(st[1], st[2],st[3],st[4],st[5],st[6],st[7],st[8]);
                        // write feedback
                        if (u != null) {
                            this.getOut().println("+ Added Meeting. Complete info MG " + u);
                        }
                    } else {
                        this.getOut().println(Main.WRONG_COMMAND+" addMeetingMG(nameMG,idPlace,description,isDraft,attendeeLimit,waitList,guestsMember,attendeeTotal)");
                    }
                }
                if (st[0].equalsIgnoreCase(Main.ADD_ANSWER)) {
                    if (st.length == 6) {

                        Answer u=mm.addAnswer(st[1], st[2],st[3],st[4],st[5]);
                        // write feedback
                        if (u != null) {
                            this.getOut().println("+ Added Answer " + u);
                        }
                    } else {
                        this.getOut().println(Main.WRONG_COMMAND+" addAnswer(MeetingDescription,emailUser,pwdUser,guests,Assistance)");
                    }
                }
                if (st[0].equalsIgnoreCase(Main.LIST_WAITING)) {
                    if (st.length == 2) {
                        mm.listMeetingAnswers(st[1]);
                    }
                    else {
                        this.getOut().println(Main.WRONG_COMMAND+" listMeetingAnswers(MeetingDescription)");
                    }

                }
            }
            catch (MeetingException e) {
                this.getOut().println(e.getMessage());
            }
            catch (Exception e) {
                e.printStackTrace(System.err);
            }

            // new line
            this.getOut().println();
        }
    } // processCommand

} // Main