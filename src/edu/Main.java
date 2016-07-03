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

import javax.xml.transform.Source;

import edu.uoc.prac.BaseFloor;
import edu.uoc.prac.BaseFloorException;
import edu.uoc.prac.Color;
import edu.uoc.prac.Company;
import edu.uoc.prac.Design;
import edu.uoc.prac.Location;
import edu.uoc.prac.Order;
import edu.uoc.prac.Piece;
import edu.uoc.prac.SyntheticPiece;
import edu.uoc.prac.TextPiece;
import edu.uoc.prac.WoolPiece;


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

    private static final String ADD_COMPANY = "addCompany";
    private static final String ADD_LOCATION = "addLocation";
    private static final String ADD_COLOR = "addColor";
    private static final String NEW_DESIGN = "newDesign";
    private static final String ADD_S_PIECE = "addSyntheticPiece";
    private static final String ADD_T_PIECE = "addTextPiece";
    private static final String ADD_W_PIECE = "addWoolPiece";
    private static final String REMOVE_PIECE = "removePiece";

    private static final String TEST_COMPOSITION = "testComposition";
    private static final String GENERATE_ORDER = "createOrder";
    private static final String TEST_NATURAL_ORDER = "testNaturalOrder";

    private static final String LIST_ORDERS = "listOrders";
    private static final String LIST_ORDERS_BY_PIECE = "listOrdersByPiece";
    private static final String HISTORY = "history";


    private BufferedReader in;
    private PrintWriter out;

    /**
     * @param in the BufferedReader to set
     */
    private void setIn(BufferedReader in) {
        this.in = in;
    }

    /**
     * @param in the BufferedReader to set
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     */
    private void setIn(String in) throws UnsupportedEncodingException,
            FileNotFoundException {
        this.setIn(this.buildReader(in));
    }

    /**
     * @param out the PrintWriter to set
     */
    private void setOut(PrintWriter out) {
        this.out = out;
    }

    /**
     * @param out the PrintWriter to set
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
     * @param fileName the output filename or 'SCREEN'
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     */
    private PrintWriter buildWriter(String fileName)
            throws UnsupportedEncodingException, FileNotFoundException {
        PrintWriter out = null;
        if (fileName.equals(SCREEN)) {
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out, "UTF-8")), true);
        } else {
            File f;
            f = new File(fileName);

            PrintStream stream = new PrintStream(new FileOutputStream(f.getAbsolutePath(), false));

            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(stream, "UTF-8")));
        }
        return out;
    }

    /**
     * Builds the input stream.
     *
     * @param fileName the input stream filename or "KEYBOARD"
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
     * @param args arguments to the program
     */
    public static void main(String[] args) {
        Main p = null;
        BaseFloor cl = new BaseFloor();
        if (args.length == 2) {
            p = new Main();
            try {
                p.setOut(args[1]);
                p.setIn(args[0]);
                p.treatInput(cl);
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
     * @param cl client instance on which to delegate
     * @throws Exception In case of non I/O error
     */
    public void treatInput(BaseFloor cl) throws Exception {
        String currentLine;
        int line = 0;
        boolean end = false;
        while (!end) {
            try {
                currentLine = in.readLine();
                line++;
                if ((currentLine != null)) {
                    processCommand(cl, currentLine);
                } else {
                    end = true;
                }
            } catch (IOException ex) {
                ex.printStackTrace(System.err);
            }
        }
    }

    /**
     * Processes the line command, delegating on 'tm'.
     *
     * @param cl          Client instance on which to delegate
     * @param currentLine the current line to process
     * @throws Exception
     */
    private void processCommand(BaseFloor cl, String currentLine)
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
        } else {
            this.getOut().println("## COMMAND ##: " + currentLine);

            boolean commandOK = false;

            try {

                //
                // ADD_COMPANY
                //
                if (st[0].equalsIgnoreCase(Main.ADD_COMPANY)) {
                    if (st.length == 2) {
                        commandOK = true;

                        Company o = cl.addCompany(st[1]);

                        // write feedback
                        if (o != null) {
                            this.getOut().println("+ New company " + o.getName());
                        }
                    }
                }

                //
                // ADD_LOCATION
                //
                else if (st[0].equalsIgnoreCase(Main.ADD_LOCATION)) {
                    if (st.length == 6) {
                        commandOK = true;

                        Location o = cl.addLocation(st[1], st[2], st[3], st[4], st[5]);

                        // write feedback
                        if (o != null) {
                            Company oo = o.getCompany();
                            this.getOut().println("+ New location " + o + ". There are " + oo.getLocations().length + " locations at company " + oo.getName());
                        }
                    } else {
                        this.getOut().println(Main.WRONG_COMMAND);
                    }
                }


                //
                // ADD_COLOR
                //
                else if (st[0].equalsIgnoreCase(Main.ADD_COLOR)) {
                    if (st.length == 5) {
                        commandOK = true;

                        Color o = cl.addColor(st[1], st[2], st[3], st[4]);

                        // write feedback
                        if (o != null) {
                            this.getOut().println("+ Stock updated with color " + o.getRgb()
                                    + ". " + cl.getStock());
                        }
                    }
                }


                //
                // NEW_DESIGN
                //
                else if (st[0].equalsIgnoreCase(NEW_DESIGN)) {
                    if (st.length == 3) {
                        commandOK = true;

                        Design o = cl.newDesign(st[1], st[2]);

                        // write feedback
                        if (o != null) {
                            this.getOut().println("+ new Design create for location " + o.getLocation() + ". " + o);
                        }
                    }
                }


                //
                // ADD_S_PIECE
                //
                else if (st[0].equalsIgnoreCase(Main.ADD_S_PIECE)) {
                    if (st.length == 6) {
                        commandOK = true;

                        SyntheticPiece o = (SyntheticPiece) cl.addSyntheticPiece(st[1], st[2], st[3], st[4], st[5]);

                        // write feedback
                        if (o != null) {
                            this.getOut().println("+ New Synthetic piece added '" + o.toString().trim() + "' for design " + o.getDesign());
                        }
                    }
                }

                //
                // ADD_T_PIECE
                //
                else if (st[0].equalsIgnoreCase(Main.ADD_T_PIECE)) {
                    if (st.length == 7) {
                        commandOK = true;

                        TextPiece o = (TextPiece) cl.addTextPiece(st[1], st[2], st[3], st[4], st[5], st[6]);

                        // write feedback
                        if (o != null) {
                            this.getOut().println("+ New Text piece added '" + o.toString().trim() + "' for design " + o.getDesign());
                        }
                    }
                }

                //
                // ADD_W_PIECE
                //
                else if (st[0].equalsIgnoreCase(Main.ADD_W_PIECE)) {
                    if (st.length == 7) {
                        commandOK = true;

                        WoolPiece o = (WoolPiece) cl.addWoolPiece(st[1], st[2], st[3], st[4], st[5], st[6]);

                        // write feedback
                        if (o != null) {
                            this.getOut().println("+ New Wool piece added '" + o.toString().trim() + "' for design " + o.getDesign());
                        }
                    }
                }

                //
                // REMOVE_PIECE
                //
                else if (st[0].equalsIgnoreCase(Main.REMOVE_PIECE)) {
                    if (st.length == 5) {
                        commandOK = true;

                        Design o = cl.removePiece(st[1], st[2], st[3], st[4]);

                        // write feedback
                        if (o != null) {
                            this.getOut().println("+ Piece removed from design " + o);
                        }
                    }
                }

                //
                // TEST_COMPOSITION
                //
                else if (st[0].equalsIgnoreCase(TEST_COMPOSITION)) {
                    if (st.length == 1) {
                        commandOK = true;

                        String o = cl.testComposition();

                        // write feedback
                        if (o != null) {
                            this.getOut().println(o);
                        }
                    }
                }


                //
                // GENERATE_ORDER
                //
                else if (st[0].equalsIgnoreCase(GENERATE_ORDER)) {
                    if (st.length == 3) {
                        commandOK = true;

                        Order o = cl.createOrder(st[1], st[2]);

                        // write feedback
                        if (o != null) {
                            this.getOut().println("+ New Order : " + o);
                        }
                    }
                }

                //
                // TEST_NATURAL_ORDER
                //
                else if (st[0].equalsIgnoreCase(TEST_NATURAL_ORDER)) {
                    if (st.length == 1) {
                        commandOK = true;

                        Object[] o = cl.testNatualOrder();

                        // write feedback
                        if ((o != null) && (o.length == 3)) {
                            if ((o[0] instanceof Comparable) && (o[0] instanceof Comparable) && (o[0] instanceof Comparable)) {
                                Comparable o0 = (Comparable) o[0];
                                Comparable o1 = (Comparable) o[1];
                                Comparable o2 = (Comparable) o[2];
                                this.getOut().println("o[0] compareto o[1] = " + o0.compareTo(o1));
                                this.getOut().println("o[1] compareto o[2] = " + o1.compareTo(o2));
                                this.getOut().println("o[2] compareto o[0] = " + o2.compareTo(o0));
                            } else {
                                this.getOut().println("ERROR: Order is not Comparable");
                            }
                        }
                    }
                }


                //
                // LIST_ORDERS
                //
                else if (st[0].equalsIgnoreCase(Main.LIST_ORDERS)) {
                    if (st.length == 1) {
                        commandOK = true;

                        String o = cl.listOrders();

                        // write feedback
                        if (o != null) {
                            this.getOut().println(o);
                        }
                    }
                }

                //
                // LIST_ORDERS_BY_PIECE
                //
                else if (st[0].equalsIgnoreCase(Main.LIST_ORDERS_BY_PIECE)) {
                    if (st.length == 1) {
                        commandOK = true;

                        String o = cl.listOrdersByPiece();

                        // write feedback
                        if (o != null) {
                            this.getOut().println(o);
                        }
                    }
                }

                //
                // HISTORY
                //
                else if (st[0].equalsIgnoreCase(Main.HISTORY)) {
                    if (st.length == 3) {
                        commandOK = true;

                        Design[] o = cl.history(st[1], st[2]);

                        // write feedback
                        if (o != null) {
                            for (Design d : o) {
                                print(d);
                            }
                        }
                    }
                } else if (st[0].equalsIgnoreCase("draw")) {
                    if (st.length == 3) {
                        commandOK = true;

                        Design o = cl.getDesign(st[1], st[2]);

                        // write feedback
                        if (o != null) {
                            print(o);
                        }
                    }
                } else {
                    this.getOut().println(Main.COMMAND_NOT_FOUND);
                }

                if (commandOK) {
                    //System.out.println("OK!");
                } else {
                    this.getOut().println(Main.WRONG_COMMAND);
                }
            } catch (BaseFloorException e) {
                this.getOut().println("ERROR. " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }

            // new line
            this.getOut().println();
        }
    } // processCommand


    /**
     * Constants definitions
     */
    private static final String COLUMN_SEPARATOR = "|";
    private static final String BASE_TILE_SEPARATOR = "-----------";
    private static final String BLANK_TILE = "     x     ";

    /**
     * Print the design layout based on its pieces
     *
     * @param d Design
     */
    private void print(Design d) {

        // summary
        this.getOut().println(d);

        // get pieces
        Piece[][] pieces = d.getTiles();

        // crete tile separator
        StringBuilder separator = new StringBuilder(COLUMN_SEPARATOR);
        for (int x = 0; x < d.getColumns(); x++) {
            separator.append(BASE_TILE_SEPARATOR).append(COLUMN_SEPARATOR);
        }
        this.getOut().println(separator.toString());

        // For all pieces
        for (int y = 0; y < pieces.length; y++) {

            this.getOut().print(COLUMN_SEPARATOR);

            for (int x = 0; x < pieces[y].length; x++) {
                Piece p = pieces[y][x];

                if (p != null) {
                    this.getOut().print(p);
                    this.getOut().print(COLUMN_SEPARATOR);
                } else {
                    this.getOut().print(BLANK_TILE);
                    this.getOut().print(COLUMN_SEPARATOR);
                }
            }

            // next line
            this.getOut().println();
            this.getOut().println(separator.toString());
        }
    }
} // Main
