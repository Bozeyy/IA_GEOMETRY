package com.smartdash.project.apprentissage.util;

import java.awt.*;
import java.awt.image.BufferedImage;

import com.smartdash.project.mvc.modele.Joueur;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.jfree.chart.*;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;

public class Statistique {
    private List<Double> moyenne = new ArrayList<>();
    private List<Double> moyenne10 = new ArrayList<>();

    private List<Integer> nbNeuronesMeilleur = new ArrayList<>();

    private List<Double> moyenneTest = new ArrayList<>();
    private Joueur meilleurJoueur;
    private DefaultCategoryDataset dataset = new DefaultCategoryDataset();


    /**
     * Méthode pour calculer la moyenne des scores des joueurs dans une liste
     * @param joueurs liste des joueurs
     * @return retourne la moyenne
     * @throws Exception renvoie une exception si le joueur est inexistant
     */
    public double calculerMoyenneDesScores(List<Joueur> joueurs) throws Exception {
        if (joueurs == null || joueurs.isEmpty()) {
            throw new Exception("Joueur null existant");
        }

        double sommeDesScores = 0;

        for (Joueur joueur : joueurs) {
            sommeDesScores += joueur.getScorePartie();
        }

        return (sommeDesScores) / (joueurs.size());
    }

    /**
     * Méthode qui permet de calculer la moyenne des 10 meilleurs
     * @param joueurs liste de joueurs
     * @return renvoie la moyenne en double
     * @throws Exception retourne une exception si le joueur est null
     */
    public double calculerMoyenne10Meilleurs(List<Joueur> joueurs) throws Exception {

        List<Joueur> copieJoueurs = new ArrayList<>(joueurs);
        copieJoueurs.sort(Comparator.comparingDouble(Joueur::getScorePartie).reversed());


        double sommeDesScores = 0;

        for (int i = 0; i < 10; i++) {
            Joueur joueur = copieJoueurs.get(i);
            sommeDesScores += joueur.getScorePartie();
        }

        return (sommeDesScores) / (10);
    }

    /**
     * Méthode qui permet d'ajouter les moyennes de la liste des joueurs
     * @param joueurs
     * @throws Exception
     */
    public void addMoyennes (List<Joueur> joueurs) throws Exception {
        this.moyenne.add(calculerMoyenneDesScores(joueurs));
        this.moyenne10.add(calculerMoyenne10Meilleurs(joueurs));
        this.nbNeuronesMeilleur.add(joueurs.get(0).getReseau().getNbNeurone());
    }

    /**
     * Méthode qui permet d'ajouter les moyennes de tests
     * @param joueurs
     * @throws Exception
     */
    public void addMoyennesTests (List<Joueur> joueurs) throws Exception {
        this.moyenneTest.add(calculerMoyenne10Meilleurs(joueurs));
        this.moyenneTest.add(calculerScoreMeilleur(joueurs));
    }

    private Double calculerScoreMeilleur(List<Joueur> joueurs) {
        List<Joueur> copieJoueurs = new ArrayList<>(joueurs);
        copieJoueurs.sort(Comparator.comparingDouble(Joueur::getScorePartie).reversed());


        Joueur joueur = copieJoueurs.get(0);
        return joueur.getScorePartie();

    }


    /**
     * Méthode qui permet de générer un PDF
     * @param pathName path de l'enregistrement
     */
    public void genererPDF (String pathName) {
        System.out.println("donne moyenne test stockés :");
        for (Double d : this.moyenneTest) {
            System.out.println(d);
        }
        System.out.println("--------------");
        String newPath = pathName.replace("enregistrement", "statistiques");

        try {
            PDDocument document = new PDDocument();

            for (int i = 0; i < moyenne.size(); i++) {
                PDPage page = new PDPage();
                document.addPage(page);

                genererPageGeneration(document, page, i);
            }

            PDPage page = new PDPage();
            document.addPage(page);
            genererPageGraphique(document, page);

            //String nom = debutStatistique();

            //System.out.println(nom);
            document.save(newPath + ".pdf");
            document.close();

            System.out.println("PDF géréré");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static String debutStatistique() throws Exception {
        //On vérifie si le dossier <enregistrement> existe
        if(!new File("src/main/resources/stats").exists()) new File("src/main/resources/stats").mkdir();

        //Récupération de la date et du temps ou le programme a été lancé
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        Date date = new Date();


        return format.format(date);
    }

    /**
     * Méthode qui permet de générer une page
     * @param document document
     * @param page page
     * @param numGeneration numéro de la génération (une page par génération)
     */
    private void genererPageGeneration(PDDocument document, PDPage page, int numGeneration) {
        double moyenne = this.moyenne.get(numGeneration);
        double moyenne10 = this.moyenne10.get(numGeneration);
        int nbNeuroneMeileur = this.nbNeuronesMeilleur.get(numGeneration);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 24);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Rapport de Génération: " + numGeneration);
            contentStream.endText();

            // Ajouter un séparateur
            contentStream.moveTo(100, 680);
            contentStream.lineTo(500, 680);
            contentStream.stroke();

            // Afficher la moyenne du score de la population
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 650);
            contentStream.showText("Moyenne du score de la population: " + moyenne);
            contentStream.newLine();
            contentStream.endText();

            // Afficher la moyenne des 10 meilleurs
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 630);  // Ajustez la position verticale en fonction de votre besoin
            contentStream.showText("Moyenne des 10 meilleurs: " + moyenne10);
            contentStream.newLine();
            contentStream.endText();

            // Afficher le nombre de neurone du meilleur
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 610);  // Ajustez la position verticale en fonction de votre besoin
            contentStream.showText("Nombre de neurones du meilleur: " + nbNeuroneMeileur);
            contentStream.newLine();
            contentStream.endText();

            // Ajouter les informations des réseaux de neurones
//            genererMeilleurReseauPDF(contentStream, generation);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
    private static void genererMeilleurReseauPDF(PDPageContentStream contentStream, List<Joueur> generation) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
        contentStream.beginText();
        contentStream.newLineAtOffset(100, 610);

        Joueur meilleurJoueur = generation.stream().max(Comparator.comparingDouble(Joueur::getScore)).orElse(null);



        if (meilleurJoueur != null) {
            contentStream.showText("Réseau de Neurones du meilleur Joueur (score : " + meilleurJoueur.getScore() + " :");
            contentStream.newLineAtOffset(0, -12);  // Ajuste le décalage vertical
            contentStream.newLine();

            Reseau reseau = meilleurJoueur.getReseau();
            String[] lines = reseau.toString().split("\\r?\\n");

            for (String line : lines) {
                contentStream.showText(line.replaceAll("\\p{Cntrl}", " "));
                contentStream.newLineAtOffset(0, -12);
            }

            contentStream.newLine();
        }

        contentStream.endText();
    }*/

    /**
     * Méthode qui permet de générer la page graphique finale
     * @param document document
     * @param page page
     * @throws IOException exception flux
     */
    private void genererPageGraphique(PDDocument document, PDPage page) throws IOException {
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

            // Titre de la page
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 24);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 750);
            contentStream.showText("Evolution des Moyennes");
            contentStream.endText();

            // Créer un graphique JFreeChart
            // Ajouter les données au dataset
            double moyenneTests = 0;
            int j = 0;
            for (int i = 0; i < this.moyenne.size(); i++) {
                double moyennePop = moyenne.get(i);
                double moyenneMeilleurs = moyenne10.get(i);
                dataset.addValue (moyennePop, "Moyenne générale", Integer.toString(i));
                dataset.addValue(moyenneMeilleurs, "Moyenne des 10 meilleurs", Integer.toString(i));

                if (i % 20 == 0) {
                    moyenneTests = moyenneTest.get(j);
                    j++;
                }
                dataset.addValue(moyenneTests, "Moyenne des 10 meilleurs sur terrains de test", Integer.toString(i));
            }

            // Créer un graphique JFreeChart
            JFreeChart chart = createChart();

            // Convertir le graphique en image BufferedImage
            BufferedImage bufferedImage = chart.createBufferedImage(500, 400);

            // Convertir l'image BufferedImage en PDImageXObject
            PDImageXObject pdImage = LosslessFactory.createFromImage(document, bufferedImage);

            // Dessiner l'image dans le contenu du PDF
            contentStream.drawImage(pdImage, 50, 230, pdImage.getWidth(), pdImage.getHeight());

            // Afficher la genaration la plus flexible
            double maxVal = this.moyenneTest.stream()
                    .mapToDouble(Double::doubleValue)
                    .max()
                    .orElse(0.0);

            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 250);  // Ajustez la position verticale en fonction de votre besoin
            contentStream.showText("Generation la plus flexible: " + this.moyenneTest.indexOf(maxVal)*20);
            contentStream.newLine();
            contentStream.endText();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Méthode qui permet de créer les chart
     * @return une chart
     */
    private JFreeChart createChart() {
        JFreeChart chart = ChartFactory.createLineChart(
                "Evolution des Moyennes",
                "Génération",
                "Moyenne du Score",
                dataset,
                PlotOrientation.VERTICAL,
                true, // include legend
                true,
                false
        );

        // Masquer les étiquettes de l'axe des x
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.getDomainAxis().setTickLabelsVisible(false);

        // Définir l'espacement entre les points pour la troisième série
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);   // Moyenne générale en bleu
        renderer.setSeriesPaint(1, Color.RED);    // Moyenne des 10 meilleurs en rouge
        renderer.setSeriesPaint(2, Color.ORANGE);  // Moyenne des 10 meilleurs sur terrains de test en vert
        renderer.setSeriesStroke(2, new BasicStroke(2.0f));
        renderer.setSeriesShapesVisible(2, true);
        renderer.setSeriesShapesFilled(2, true);
        renderer.setSeriesLinesVisible(2, true);

        return chart;
    }
}
