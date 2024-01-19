package com.smartdash.project.apprentissage.util;

import java.awt.image.BufferedImage;

import com.smartdash.project.IA.Reseau;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.jfree.chart.*;
import com.smartdash.project.modele.Joueur;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Statistique {

    private List<List<Joueur>> generations = new ArrayList<>();

    private DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    /**
     * Méthode pour calculer la moyenne des scores des joueurs dans une liste
     * @param joueurs liste des joueurs
     * @return retourne la moyenne
     * @throws Exception renvoie une exception si le joueur est inexistant
     */
    public static double calculerMoyenneDesScores(List<Joueur> joueurs) throws Exception {
        if (joueurs == null || joueurs.isEmpty()) {
            throw new Exception("Joueur null existant");
        }

        double sommeDesScores = 0;

        for (Joueur joueur : joueurs) {
            if(joueur.getScore()<0) throw new Exception("Score inférieur à 0");
            sommeDesScores += joueur.getScore();
        }

        return (sommeDesScores) / (joueurs.size());
    }

    /**
     * Méthode qui permet de calculer la moyenne des 10 meilleurs
     * @param joueurs liste de joueurs
     * @return renvoie la moyenne en double
     * @throws Exception retourne une exception si le joueur est null
     */
    public static double calculerMoyenne10Meilleurs(List<Joueur> joueurs) throws Exception {

        List<Joueur> copieJoueurs = new ArrayList<>(joueurs);
        copieJoueurs.sort(Comparator.comparingDouble(Joueur::getScore).reversed());


        double sommeDesScores = 0;

        for (int i = 0; i < 10; i++) {
            Joueur joueur = copieJoueurs.get(i);
            if(joueur.getScore()<0) throw new Exception("Score inférieur à 0");
            sommeDesScores += joueur.getScore();
        }

        return (sommeDesScores) / (10);
    }

    /**
     * Méthode qui permet de calculer les 10 meilleurs scores moyen
     * @param joueurs joueur
     * @return retourne un double qui est la moyenne
     */
    public static double calculerMoyenne10MeilleursScoreMoyen(List<Joueur> joueurs){

        List<Joueur> copieJoueurs = new ArrayList<>(joueurs);
        copieJoueurs.sort(Comparator.comparingDouble(Joueur::getScoreMoyen).reversed());


        //double sommeDesScoresMoyen = 0;

        /**
        for (int i = 0; i < 10; i++) {
            Joueur joueur = copieJoueurs.get(i);

            if(joueur.getScoreMoyen()<0) throw new Exception("Score inférieur à 0");

            sommeDesScoresMoyen += joueur.getScoreMoyen();
            System.out.println("Score : "+joueur.getScoreMoyen());
            //System.out.println(joueur.getReseau());
            //System.out.println(joueur.getScore());
        }*/

        return copieJoueurs.stream()
                .limit(10)
                .peek(joueur -> {
                    if (joueur.getScoreMoyen() < 0) throw new RuntimeException("Score inférieur à 0");
                })
                .mapToDouble(Joueur::getScoreMoyen)
                .average()
                .orElse(0.0);
    }

    public void addGeneration (List<Joueur> joueurs) {
        this.generations.add(joueurs);
    }

    public void genererPDF () {
        try {
            PDDocument document = new PDDocument();

            for (int i = 0; i < generations.size(); i++) {
                PDPage page = new PDPage();
                document.addPage(page);

                genererPageGeneration(document, page, i);
            }

            PDPage page = new PDPage();
            document.addPage(page);
            genererPageGraphique(document, page);

            document.save("src/main/resources/statistiques.pdf");
            document.close();

            System.out.println("PDF géréré");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void genererPageGeneration(PDDocument document, PDPage page, int numGeneration) {
        List<Joueur> generation = this.generations.get(numGeneration);

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
            contentStream.showText("Moyenne du score de la population: " + calculerMoyenneDesScores(generation));
            contentStream.newLine();
            contentStream.endText();

            // Afficher la moyenne des 10 meilleurs
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 630);  // Ajustez la position verticale en fonction de votre besoin
            contentStream.showText("Moyenne des 10 meilleurs: " + calculerMoyenne10Meilleurs(generation));
            contentStream.newLine();
            contentStream.endText();

            // Ajouter les informations des réseaux de neurones
            genererMeilleurReseauPDF(contentStream, generation);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


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
    }

    private void genererPageGraphique(PDDocument document, PDPage page) throws IOException {
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

            // Titre de la page
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 24);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Evolution des Moyennes");
            contentStream.endText();

            // Créer un graphique JFreeChart
            // Ajouter les données au dataset
            for (int i = 0; i < this.generations.size(); i++) {
                double moyennePop = calculerMoyenneDesScores(generations.get(i));
                double moyenneMeilleurs = calculerMoyenne10Meilleurs(generations.get(i));

                dataset.addValue(moyennePop, "Moyenne générale", Integer.toString(i));
                dataset.addValue(moyenneMeilleurs, "Moyenne des 10 meilleurs", Integer.toString(i));
            }

            // Créer un graphique JFreeChart
            JFreeChart chart = createChart();

            // Convertir le graphique en image BufferedImage
            BufferedImage bufferedImage = chart.createBufferedImage(500, 400);

            // Convertir l'image BufferedImage en PDImageXObject
            PDImageXObject pdImage = LosslessFactory.createFromImage(document, bufferedImage);

            // Dessiner l'image dans le contenu du PDF
            contentStream.drawImage(pdImage, 100, 400, pdImage.getWidth(), pdImage.getHeight());


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


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

        return chart;
    }
}
