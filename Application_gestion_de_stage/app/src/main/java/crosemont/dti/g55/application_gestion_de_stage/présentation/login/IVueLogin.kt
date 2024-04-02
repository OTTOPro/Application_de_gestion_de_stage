package crosemont.dti.g55.application_gestion_de_stage.présentation.login

interface IVueLogin {
	fun naviguer_vers_accueil()
	fun afficher_erreur( message: String )

	fun obtenir_courriel() : String
	fun obtenir_mot_de_passe() : String
}
