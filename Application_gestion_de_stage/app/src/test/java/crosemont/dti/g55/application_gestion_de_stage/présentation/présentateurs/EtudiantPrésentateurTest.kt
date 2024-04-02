package crosemont.dti.g55.application_gestion_de_stage.présentation.présentateurs

import crosemont.dti.g55.application_gestion_de_stage.domaine.Etudiant
import crosemont.dti.g55.application_gestion_de_stage.ecran_profil_etudiant
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
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EtudiantPrésentateurTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `étant donné un Présentateur nouvellement instancié, lorsqu'on essaie de récupérer les données , on obtient une mise a jour de la vue avec la liste des etudiants recuperée par le modèle`() = runTest {

        var source: Source_bidon = Mockito.mock(Source_bidon::class.java)
        val modèle = Modèle(source)
        val listeEtudiant = mutableListOf<Etudiant>()
        val vue: ecran_profil_etudiant = Mockito.mock(ecran_profil_etudiant()::class.java)

        val presentateur = EtudiantPrésentateur(vue,modèle)


        Mockito.`when`(source.obtenirDonnéesEtudiant("")).thenReturn(listeEtudiant)

        presentateur.chargerEtudiant()
        Thread.sleep(2000)
        Mockito.verify(vue).afficherEtudiant(listeEtudiant)
        Mockito.verify(vue).afficherEcranChargement(false)

    }
}