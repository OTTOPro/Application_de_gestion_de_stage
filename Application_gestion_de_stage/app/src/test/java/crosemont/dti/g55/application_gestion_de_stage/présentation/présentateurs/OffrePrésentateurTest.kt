package crosemont.dti.g55.application_gestion_de_stage.présentation.présentateurs

import crosemont.dti.g55.application_gestion_de_stage.details_offre
import crosemont.dti.g55.application_gestion_de_stage.domaine.Candidature
import crosemont.dti.g55.application_gestion_de_stage.domaine.Employeur
import crosemont.dti.g55.application_gestion_de_stage.domaine.Entreprise
import crosemont.dti.g55.application_gestion_de_stage.domaine.Etudiant
import crosemont.dti.g55.application_gestion_de_stage.domaine.ModeEmploi
import crosemont.dti.g55.application_gestion_de_stage.domaine.Offre
import crosemont.dti.g55.application_gestion_de_stage.domaine.ProfilInformatique
import crosemont.dti.g55.application_gestion_de_stage.domaine.ÉtatCandiature
import crosemont.dti.g55.application_gestion_de_stage.ecran_recherche
import crosemont.dti.g55.application_gestion_de_stage.présentation.modèles.Modèle
import crosemont.dti.g55.application_gestion_de_stage.sourceDeDonnées.Source_bidon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertTrue


@RunWith(MockitoJUnitRunner::class)
class OffrePrésentateurTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `étant donné un Présentateur nouvellement instancié, lorsqu'on essaie de récupérer les données , on obtient une mise a jour de la vue avec la liste des offres recuperée par le modèle`() = runTest {

        var source: Source_bidon = mock(Source_bidon::class.java)
        val modèle = Modèle(source)
        val listeOffre = mutableListOf<Offre>()
        val vue: ecran_recherche = mock(ecran_recherche()::class.java)

        val presentateur = OffrePrésentateur(vue,modèle)


        `when`(source.obtenirDonnéesOffres()).thenReturn(listeOffre)

        presentateur.chargerToutesLesOffres()
        Thread.sleep(7000)
        verify(vue).afficherOffres(listeOffre)
        verify(vue).afficherEcranChargement(false)

    }

    @Test
    fun `étant donné un Présentateur nouvellement instancié, lorsqu'on essaye de chercher des offres,on obtient une mise a jour de la vue avec la liste des offres filtrées`() = runTest {

        val source: Source_bidon = mock(Source_bidon::class.java)
        val modèle = Modèle(source)
        val listeOffre = mutableListOf(
            Offre(
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
        )
        val vue: ecran_recherche = mock(ecran_recherche::class.java)

        val presentateur = OffrePrésentateur(vue, modèle)

        `when`(source.obtenirDonnéesOffres()).thenReturn(listeOffre)
        presentateur.chargerToutesLesOffres()

        Thread.sleep(7000)

        verify(vue).afficherOffres(listeOffre)

        presentateur.filtrerOffres("Développeur")
        verify(vue, times(1)).afficherOffres(listOf(listeOffre[0]))

    }

}