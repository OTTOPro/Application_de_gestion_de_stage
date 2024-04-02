package crosemont.dti.g55.application_gestion_de_stage.présentation.modèles

import crosemont.dti.g55.application_gestion_de_stage.domaine.Candidature
import crosemont.dti.g55.application_gestion_de_stage.domaine.Employeur
import crosemont.dti.g55.application_gestion_de_stage.domaine.Entreprise
import crosemont.dti.g55.application_gestion_de_stage.domaine.Etudiant
import crosemont.dti.g55.application_gestion_de_stage.domaine.ModeEmploi
import crosemont.dti.g55.application_gestion_de_stage.domaine.Offre
import crosemont.dti.g55.application_gestion_de_stage.domaine.ProfilInformatique
import crosemont.dti.g55.application_gestion_de_stage.domaine.Stage
import crosemont.dti.g55.application_gestion_de_stage.domaine.ÉtatCandiature
import crosemont.dti.g55.application_gestion_de_stage.sourceDeDonnées.Source_bidon
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.lenient
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class Modèle_test {

    @Test
    fun `étant donné un modéle d'offres de stage, lorsqu'on essaye de recuperer les données a partir de ce modéle, on obtient ces données`() = runTest {

        var source: Source_bidon = mock(Source_bidon::class.java)
        val modèle = Modèle(source)
        val listeOffre = mutableListOf<Offre>()

        `when`(source.obtenirDonnéesOffres()).thenReturn(listeOffre)

        val offres = modèle.obtenirToutesLesOffres()
        assertEquals(listeOffre,offres)

    }

    @Test
    fun `étant donné un modéle de profil d'étudiants, lorsqu'on essaye de recuperer les données a partir de ce modéle, on obtient ces données`() = runTest {

        var source: Source_bidon = mock(Source_bidon::class.java)
        val modèle = Modèle(source)
        val listeEtudiant = mutableListOf<Etudiant>()

        `when`(source.obtenirDonnéesEtudiant(anyString())).thenReturn(listeEtudiant)

        val etudiant = modèle.obtenirEtudiant()

        assertEquals(listeEtudiant,etudiant)

    }


    @Test
    fun `étant donné un modéle de candidatures, lorsqu'on essaye de recuperer les données a partir de ce modéle, on obtient ces données`() = runTest{

        var source: Source_bidon = mock(Source_bidon::class.java)
        val modèle = Modèle(source)
        val listeCandidature = mutableListOf<Candidature>()

        `when`(source.obtenirDonnéesCandidatures(anyString())).thenReturn(listeCandidature)

        val candidatures = modèle.obtenirCandidatures()

        assertEquals(listeCandidature,candidatures)

    }

    @Test
    fun `étant donné un modèle de candidatures, lorsqu'on essaye d'ajouter une candidature , le modèle est correctement appelé`() = runTest{

        var source: Source_bidon = mock(Source_bidon::class.java)
        val modèle = Modèle(source)

        val offre = Offre(
            idOffre = 1,
            employeur = Employeur(
                codeUtilisateur = "123",
                codeEntreprise = Entreprise(1,"CGI", "456 Rue de l'Entreprise"),
                nomUtilisateur = "NomEmployeur",
                prénomUtilisateur = "PrénomEmployeur",
                courrielUtilisateur = "nom.employeur@example.com",
                téléphoneUtilisateur = "987654321"),
            titrePoste = "Développeur Android",
            modeEmploi = ModeEmploi.PRÉSENTIEL,
            description = "Description de l'offre 1",
            offreSauvegardée = false,
            candidatures = mutableListOf()
        )

        modèle.ajouterCandidature(offre.idOffre)
        verify(source).AjouterUneCandidature("", offre.idOffre)

    }

    @Test
    fun `étant donné un modele de candidatures, lorsque on supprime une candidature, le modèle est correctement appelé`() = runTest {

        val source: Source_bidon = mock(Source_bidon::class.java)
        val modèle = Modèle(source)
        var offre = Offre(
            idOffre = 1,
            employeur = Employeur(
                codeUtilisateur = "123",
                codeEntreprise = Entreprise(1,"CGI", "456 Rue de l'Entreprise"),
                nomUtilisateur = "NomEmployeur",
                prénomUtilisateur = "PrénomEmployeur",
                courrielUtilisateur = "nom.employeur@example.com",
                téléphoneUtilisateur = "987654321"),
            titrePoste = "Développeur Android",
            modeEmploi = ModeEmploi.PRÉSENTIEL,
            description = "Description de l'offre 1",
            offreSauvegardée = false,
            candidatures = mutableListOf()
        )
        val etudiant =  Etudiant(
            codeUtilisateur = "123",
            nomUtilisateur = "Nom",
            prénomUtilisateur = "Prénom",
            courrielUtilisateur = "nom.prenom@example.com",
            téléphoneUtilisateur = "123456789",
            profilInformatique = ProfilInformatique.PROGRAMMATION,
            stageIntégration = true,
            adresseÉtudiant = "123 Rue de l'Étudiant"
        )
        val etat = ÉtatCandiature.Encours
        val candidature = Candidature(1,offre, etudiant, etat)
        val listeCandidature = mutableListOf<Candidature>()

        listeCandidature.add(candidature)

        `when`(source.obtenirDonnéesCandidatures("")).thenReturn(listeCandidature)

        modèle.supprimerCandidature(1)

        Thread.sleep(1000)

        verify(source).supprimerCandidature("", 1)

    }


}