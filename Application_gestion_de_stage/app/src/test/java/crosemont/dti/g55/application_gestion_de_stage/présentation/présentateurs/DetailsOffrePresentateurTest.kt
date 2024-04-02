package crosemont.dti.g55.application_gestion_de_stage.présentation.présentateurs

import crosemont.dti.g55.application_gestion_de_stage.details_offre
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
class DetailsOffrePresentateurTest {

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
    fun `étant donné un presentateur de details d'une offre , lorsque on ajoute une candidature, le modèle est correctement appelé`() = runTest {

        val source: Source_bidon = Mockito.mock(Source_bidon::class.java)
        val modèle = Modèle(source)
        val vue: details_offre = Mockito.mock(details_offre::class.java)

        val presentateur = DetailsOffrePrésentateur(vue, modèle)
        presentateur.ajouterUneCandidature(1)

        Thread.sleep(1000)

        Mockito.verify(source).AjouterUneCandidature("", 1)

    }


}