package crosemont.dti.g55.application_gestion_de_stage.présentation.présentateurs

import crosemont.dti.g55.application_gestion_de_stage.details_offre
import crosemont.dti.g55.application_gestion_de_stage.domaine.Candidature
import crosemont.dti.g55.application_gestion_de_stage.domaine.Employeur
import crosemont.dti.g55.application_gestion_de_stage.domaine.Entreprise
import crosemont.dti.g55.application_gestion_de_stage.domaine.Etudiant
import crosemont.dti.g55.application_gestion_de_stage.domaine.ModeEmploi
import crosemont.dti.g55.application_gestion_de_stage.domaine.Offre
import crosemont.dti.g55.application_gestion_de_stage.domaine.ProfilInformatique
import crosemont.dti.g55.application_gestion_de_stage.domaine.Stage
import crosemont.dti.g55.application_gestion_de_stage.domaine.ÉtatCandiature
import crosemont.dti.g55.application_gestion_de_stage.ecran_candidatures_et_offres_sauvegardees
import crosemont.dti.g55.application_gestion_de_stage.ecran_stage
import crosemont.dti.g55.application_gestion_de_stage.présentation.modèles.Modèle
import crosemont.dti.g55.application_gestion_de_stage.sourceDeDonnées.Source_bidon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.anyString
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CandidaturePrésentateurTest {

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
    fun `étant donné un Présentateur nouvellement instancié, lorsqu'on essaie de récupérer les données , on obtient une mise a jour de la vue avec la liste des candidatures recuperée par le modèle`() = runTest {

        var source: Source_bidon = Mockito.mock(Source_bidon::class.java)
        val modèle = Modèle(source)
        val listeCandidature = mutableListOf<Candidature>()
        val vue: ecran_candidatures_et_offres_sauvegardees = Mockito.mock(ecran_candidatures_et_offres_sauvegardees()::class.java)

        val presentateur = CandidaturePrésentateur(vue, modèle)


        Mockito.`when`(modèle.obtenirCandidatures()).thenReturn(listeCandidature)

        presentateur.obtenirCandidatures()
        Thread.sleep(3000)
        verify(vue).afficherCandidatures(listeCandidature)
        verify(vue).afficherEcranChargement(false)

    }

    @Test
    fun `étant donné un presentateur de candidature, lorsque on supprime une candidature, le modèle est correctement appelé`() = runTest {

        val source: Source_bidon = Mockito.mock(Source_bidon::class.java)
        val modèle = Modèle(source)
        val vue: ecran_candidatures_et_offres_sauvegardees = Mockito.mock(ecran_candidatures_et_offres_sauvegardees::class.java)

        val presentateur = CandidaturePrésentateur(vue, modèle)

        presentateur.supprimerLaCandidature(1)

        Thread.sleep(1000)

        verify(source).supprimerCandidature("", 1)

    }

}