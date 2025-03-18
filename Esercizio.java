//LEGGERE LE ISTRUZIONI NEL FILE README.md

//Import di Classi Java necessarie al funzionamento del programma
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;


class Esercizio {
    private static Scanner input = new Scanner(System.in);
    private static Scanner infile;
    private static FileWriter outfile;

    // Il programma parte con una chiamata a main().
    public static void main(String[] args) throws Exception {
        int c;
        int nv, np, i;
        String e;

        // Inizialmente non ci sono libri in biblioteca.
        nv = 0;
        np = 0;

        // Alloco più spazio rispetto alla dimensione effettiva perchè sul vettore saranno effettuate operazioni di inserimento e cancellazione. Supponiamo di avere massimo 100 libri in biblioteca.
        String[] v = new String[100];
        String[] p = new String[100];

        // Carico da File i libri presenti in biblioteca e quelli prestati.
        nv = caricaVettore(v, nv, "Libri.txt");
        np = caricaVettore(p, np, "Prestiti.txt");
        do {
            c = leggiComando();
            if (c == 1) {
                System.out.println("Nuovo libro da inserire in biblioteca:");
                e = input.nextLine();
                nv = inserisciInVettore(v, nv, e, 0);
            }
            if (c == 2) {
                if (nv > 0) {
                    visualizzaVettore(v, nv);
                } else {
                    System.out.println("Non ci sono libri in biblioteca");
                }
            }
            if (c == 3) {
                if (np > 0) {
                    visualizzaVettore(p, np);
                } else {
                    System.out.println("Non ci sono libri in prestito");
                }
            }
            if (c == 4) {
                visualizzaVettore(v, nv);
                System.out.println("Titolo del libro (o una sua parola) da prendere in prestito: ");
                e = input.nextLine();
                i = ricercaNelVettore(v, nv, e);
                if (i >= 0) {
                    System.out.println("Libro scelto: " + System.lineSeparator() + i + ":" + v[i]);
                    np = inserisciInVettore(p, np, v[i], 0);
                    nv = eliminaDaVettore(v, nv, i);
                } else {
                    System.out.println("Libro " + e + " non trovato.");
                }
            }
            if (c == 5) {
                if (np > 0) {
                    visualizzaVettore(p, np);
                    do {
                        System.out.println("Indice del libro da restituire: ");
                        i = Integer.parseInt(input.nextLine());
                    } while (i >= np);
                    System.out.println("Libro restituito: " + System.lineSeparator() + i + ":" + p[i]);
                    nv = inserisciInVettore(v, nv, p[i], 0);
                    np = eliminaDaVettore(p, np, i);
                } else {
                    System.out.println("Non ci sono libri in prestito");
                }
            }
        } while (c != 6);

        // Salvo i libri in biblioteca ed i prestiti in dei file, in modo da poter ripristinare lo stato dell'applicazione alla prossima apertura del programma.
        salvaVettore(v, nv, "Libri.txt");
        salvaVettore(p, np, "Prestiti.txt");
    }
    
    public static int caricaVettore(String[] v, int nv, String nomeFile) throws Exception {
        int n, i;

        infile = new Scanner(new FileReader(nomeFile));
        n = Integer.parseInt(infile.nextLine());
        i = 0;
        while (i < n) {
            v[i] = infile.nextLine();
            ++i;
        }
        infile.close();
        
        return n;
    }
    
    public static void salvaVettore(String[] v, int nv, String nomeFile) throws Exception {
        int i;

        outfile = new FileWriter(nomeFile);
        //Nel file scrivo i numeri come stringa
        outfile.write( Integer.toString(nv) );
        outfile.write(System.lineSeparator()); //Scrivo nel file il ritorno a capo

        i = 0;
        while (i < nv) {
            //Nel file scrivo i numeri come stringa
            outfile.write( v[i] );
            outfile.write(System.lineSeparator()); //Scrivo nel file il ritorno a capo
            ++i;
        }
        outfile.close();
    }


    public static int eliminaDaVettore(String[] v, int n, int ie) {
        int i, n2;

        n2 = n - 1;
        i = ie;
        while (i <= n - 2) {
            v[i] = v[i + 1];
            i = i + 1;
        }
        
        return n2;
    }
    
    public static int inizializzaVettoreLibri(String[] v, int n) {
        n = inserisciInVettore(v, n, "La bellezza e il coraggio - Paolo Comentale", n);
        n = inserisciInVettore(v, n, "Wonder - R.J. Palacio", n);
        n = inserisciInVettore(v, n, "Delitto e Castigo - Fëdor Dostoevskij", n);
        n = inserisciInVettore(v, n, "Hackers: Gli eroi della rivoluzione informatica - Steven Levy", n);
        n = inserisciInVettore(v, n, "Steve Jobs - Walter Isaacson", n);
        
        return n;
    }
    
    public static int inserisciInVettore(String[] v, int n, String e, int ie) {
        int i, n2;

        n2 = n + 1;
        i = n2 - 1;
        while (i >= ie + 1) {
            v[i] = v[i - 1];
            i = i - 1;
        }
        v[ie] = e;
        
        return n2;
    }
    
    public static int leggiComando() {
        int c;

        do {
            System.out.println(
                "1) Nuovo libro in biblioteca" + System.lineSeparator() + 
                "2) Visualizza libri in biblioteca" + System.lineSeparator() + 
                "3) Visualizza libri in prestito" + System.lineSeparator() +
                "4) Prendi in prestito un libro" + System.lineSeparator() +
                "5) Restituisci un libro" + System.lineSeparator() + 
                "6) Esci dal programma");
            c = Integer.parseInt(input.nextLine());
        } while (c < 1 || 6 < c);
        
        return c;
    }
    
    public static int ricercaNelVettore(String[] v, int n, String valore) {
        int i, iTrovato;

        i = 0;
        iTrovato = -1;
        while (i < n && iTrovato == -1) {

            // Controllo se valore è una sottostringa di V[i]
            if ( v[i].indexOf(valore) != -1) {
                iTrovato = i;
            }
            i = i + 1;
        }
        
        return iTrovato;
    }
        
    public static void visualizzaVettore(String[] v, int n) {
        int i;
        System.out.println();
        System.out.println("-----------------------");
        i = 0;
        while (i < n) {
            System.out.println(Integer.toString(i) + ": " + v[i]);
            i = i + 1;
        }
        System.out.println("-----------------------");
        System.out.println();

    }
}
