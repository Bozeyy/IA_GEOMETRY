package com.smartdash.project.apprentissage.util;

import com.smartdash.project.IA.Module;
import com.smartdash.project.IA.neurones.Neurone;
import com.smartdash.project.IA.NeuroneFabrique;
import com.smartdash.project.IA.Reseau;
import com.smartdash.project.mvc.modele.Joueur;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Enregistrement {
    private static Random random = new Random();

    /**
     * Méthode qui permet de commencer un enregistrement et d'avoir le nom du fichier
     * @return retourne un string avec le path
     * @throws Exception IO exception
     */
    public static String debutEnregistrement() throws Exception {
        //On vérifie si le dossier <enregistrement> existe
        if(!new File("src/main/resources/enregistrement").exists()) new File("src/main/resources/enregistrement").mkdir();

        //Récupération de la date et du temps ou le programme a été lancé
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        Date date = new Date();
        String dateHeureCourante = format.format(date);

        System.out.println("Date de début d'apprentissage : " + dateHeureCourante);

        //création du dossier de sauvegarde de l'apprentissage
        String pathname = "src/main/resources/enregistrement/" + dateHeureCourante + "_id" + random.nextInt(1000);
        File dossier = new File(pathname);

        try {
            if (!dossier.exists()) {
                if (dossier.mkdir()) {
                    System.out.println("Dossier créé à l'emplacement : " + pathname);
                    return pathname;
                } else {
                    throw new Exception("Erreur lors de la création du dossier");
                }
            } else {
                throw new Exception("Le dossier existe déjà");
            }
        } catch (SecurityException e) {
            System.out.println(e.getMessage());
        }

        return "";
    }

    /**
     * Méthode qui permet de générer un fichier de sérialisation
     * @param pathname path ou on enregistre le fichier
     * @param generation nb de la génération qu'on enregistre
     * @param population liste de la population qu'on enregistre
     * @throws Exception IO exception
     */
    public static void generationEnregistrement(String pathname, int generation, List<Joueur> population) throws Exception {

        //création du fichier de sauvegarde d'une generation
        String pathnameGeneration = pathname + "/generation_" + generation + ".txt";
        File fichier = new File(pathnameGeneration);

        //On rempli le fichier avec la population en chaîne de caractère
        String popToStrong = populationToString(population);

        //On écrit dans le fichier
        try(FileWriter writer = new FileWriter(fichier))
        {
            writer.write(popToStrong);

            //On ferme le fichier
            writer.close();
        }
        catch (IOException e)
        {
            throw new Exception("Erreur lors de l'écriture dans le fichier");
        }


    }

    /**
     * Méthode qui permet de récupérer un joueur d'un fichier sérialiser
     * @param pathname path du fichier
     * @param joueur nb de joueur
     * @return retourne le joueur
     * @throws Exception IO exception
     */
    public static Joueur recupererJoueurGeneration(String pathname,int joueur) throws Exception {

        //On retourne la population
        List<Joueur> population = Enregistrement.stringToPopulation(pathname);

        //On récupère le joueur
        if(joueur < population.size())
        {
            return new Joueur(population.get(joueur).getReseau().clone());
        }
        else
            throw new IllegalArgumentException("Le joueur n'a pas été trouvé");
    }

    /**
     * Méthode qui permet de convertir un fichier sérialisé en une liste de joueur
     * @param pathname path du fichier sérialisé
     * @return retourne la liste de Joueur
     * @throws Exception IO exception
     */
    public static List<Joueur> stringToPopulation(String pathname) throws Exception {

        try(BufferedReader r = new BufferedReader(new FileReader(pathname))){
            //On crée la population
            List<Joueur> population = new ArrayList<>();

            //On lit la première ligne
            String line = r.readLine();

            //On parcourt le fichier
            while (line != null) {

                //On récupère les attributs
                String[] attributs = line.split("\\|");

                //On récupère le score
                double score = Double.parseDouble(attributs[1]);

                //On récupère le reseau
                Reseau reseau = new Reseau();

                //On récupère les modules
                String[] modules = attributs[2].split("_");

                //On parcourt les modules
                for (String module : modules) {

                    //On récupère les neurones
                    String[] neurones = module.split("/");

                    //On créer un module
                    Module m = new Module();

                    //On parcourt les neurones
                    for (String neurone : neurones) {

                        //On récupère les coordonnées et le type
                        String[] attributsNeurone = neurone.split(",");

                        //On ajoute les attributs au neurone
                        int x = Integer.parseInt(attributsNeurone[0]);
                        int y = Integer.parseInt(attributsNeurone[1]);
                        char type = attributsNeurone[2].charAt(0);

                        //On ajoute le neurone au module
                        m.addNeurone(NeuroneFabrique.genererNeuronneType(x, y, type));
                    }
                    //On ajoute le module au reseau
                    reseau.addModule(m);
                }
                //On ajoute le joueur à la population
                Joueur e = new Joueur(reseau);
                e.setScoreApprentissage(score);
                population.add(e);

                //On lit la ligne suivante
                line = r.readLine();
            }

            //On retourne la population
            return population;
        } catch (FileNotFoundException e) {
            throw new Exception("Le fichier n'a pas été trouvé : " + pathname, e);
        } catch (IOException e) {
            throw new Exception("Erreur lors de la lecture du fichier : " + pathname, e);
        } catch (Exception e) {
            throw new Exception("Une erreur s'est produite lors de la lecture du fichier : " + pathname, e);
        }
    }

    /**
     * Méthode qui permet de transformer une liste de Joueur en un string
     * @param population liste de Joueur
     * @return retourne le string correspondant
     */
    public static String populationToString(List<Joueur> population) {

        //On trie la population en fonction de leur resultat (decroissant)
        population.sort(Comparator.comparing(Joueur::getScorePartie).reversed());

        //On créer un stringbuilder pour créer la chaine de caractère
        StringBuilder sb = new StringBuilder();

        //Initialisation du rang
        int rang = 1;

        //On parcourt la population
        for (Joueur j : population) {

            //Rang
            sb.append(rang);

            //Séparateur attribut
            sb.append("|");

            //Score
            sb.append(j.getScorePartie());

            //Séparateur attribut
            sb.append("|");

            //On récupère le reseau du joueur
            Reseau r = j.getReseau();

            //On parcourt les modules du reseau
            int separateurModule = 0;
            for(Module m : r.getModules()) {

                //On parcourt les neurones du module
                for (Neurone n : m.getNeurones()) {

                    //Coordonnée X
                    sb.append(n.getX());

                    //Séparateur attribut neurone
                    sb.append(",");

                    //Coordonnée Y
                    sb.append(n.getY());

                    //Séparateur attribut neurone
                    sb.append(",");

                    //Type
                    sb.append(n.getType());

                    //Séparateur neurone
                    if (m.getNeurones().indexOf(n) != m.getNeurones().size() - 1) sb.append("/");
                }
                //Séparateur module
                if (separateurModule < r.getModules().size() -1) sb.append("_");
                separateurModule++;

            }

            //Séparateur joueur
            sb.append("\n");

            //On incrémente le rang
            rang++;
        }

        //On retourne la chaine de caractère
        return sb.toString();
    }
}
