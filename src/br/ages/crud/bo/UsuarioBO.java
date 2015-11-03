package br.ages.crud.bo;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ages.crud.dao.UsuarioDAO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.TipoUsuario;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.MensagemContantes;
import br.ages.crud.validator.LoginValidator;
import br.ages.crud.validator.SenhaValidator;

/**
 * Gerencia os comportamentos de neg�cio do Usu�rio Associa os par�metros da
 * tela as propriedades da classe
 * 
 * @author C�ssio Trindade
 * 
 */
public class UsuarioBO {
	UsuarioDAO usuarioDAO = null;

	public UsuarioBO() {
		usuarioDAO = new UsuarioDAO();
	}

	/**
	 * Valida Usu�rio no sistema
	 * 
	 * @param request
	 * @return
	 * @throws NegocioException
	 */
	public boolean validaUsuario(Usuario usuario) throws NegocioException {
		boolean isValido = false;
		try {
			// valida se o usu�rio est� na base
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			isValido = usuarioDAO.validarUsuario(usuario);
			if (!isValido) {
				throw new NegocioException(MensagemContantes.MSG_ERR_USUARIO_SENHA_INVALIDOS);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}

		return isValido;

	}

	/**
	 * Valida os dados de usu�rio na tela de cadastro com erros aglutinados
	 * 
	 * @param usuario
	 * @return
	 * @throws NegocioException
	 */
	public boolean validaCadastroUsuarioA(Usuario usuario) throws NegocioException {
		boolean isValido = true;
		StringBuilder msg = new StringBuilder();

		try {
			// valida campos est�o preenchidos corretamente
			// Matricula
			/*if (usuario.getMatricula() == null || "".equals(usuario.getMatricula())) {
				isValido = false;
				msg.append(MensagemContantes.MSG_ERR_CAMPO_OBRIGATORIO.replace("?", "Matricula ").concat("<br/>"));

			}*/
			if (!usuario.getMatricula().matches("\\d{9}")){
				isValido = false;
				msg.append(MensagemContantes.MSG_ERR_MATRICULA_INVALIDA.replace("?", "Matricula ").concat("<br/>"));
			}
			// Nome
			if (usuario.getNome() == null || "".equals(usuario.getNome())) {
				isValido = false;
				msg.append(MensagemContantes.MSG_ERR_CAMPO_NOME_OBRIGATORIO + "<br/>");
			}
			// Usu�rio
			if (usuario.getUsuario() == null || "".equals(usuario.getUsuario())) {
				isValido = false;
				msg.append(MensagemContantes.MSG_ERR_CAMPO_OBRIGATORIO.replace("?", "Usuario ").concat("<br/>"));
			}
			if (!usuario.getEmail().matches("\\S+@[a-zA-Z]+(.[a-z]+)+")) {
				isValido = false;
				msg.append(MensagemContantes.MSG_ERR_EMAIL_INVALIDO.replace("?", "Email ").concat("<br/>"));
			}
			if(!usuario.getNome().matches("(([A-Z][a-z]*)\\s{0,1})*")){
				isValido = false;
				msg.append(MensagemContantes.MSG_ERR_NOME_INVALIDO.replace("?", "Nome ").concat("<br/>"));
			}
			// Senha
			Map<String, Object> valores = new HashMap<>();
			valores.put("Senha", usuario.getSenha());
			if (!new SenhaValidator().validar(valores)) {
				isValido = false;
			}

			// flag administrador
			/*			if (usuario.getPerfilAcesso() == null || "".equals(usuario.getPerfilAcesso())) {
				isValido = false;
				msg.append(MensagemContantes.MSG_ERR_CAMPO_OBRIGATORIO.replace("?", "Flag Administrador").concat("<br/>"));
			}
			// tipo usuario
			if (usuario.getTipoUsuario() == null || "".equals(usuario.getTipoUsuario())) {
				isValido = false;
				msg.append(MensagemContantes.MSG_ERR_CAMPO_OBRIGATORIO.replace("?", "Flag Tipo Usu�rio").concat("<br/>"));
			}*/

			// valida se Pessoa esta ok
			if (!isValido) {
				throw new NegocioException(msg.append(MensagemContantes.MSG_ERR_PESSOA_DADOS_INVALIDOS).toString());
			}
			//

		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}

		return isValido;

	}

	/**
	 * Valida os dados de usu�rio na tela de cadastro.
	 * 
	 * @param usuario
	 * @return
	 * @throws NegocioException
	 */

	/**
	 * Cadastra Usuario em n�vel de neg�cio, chamando o DAO
	 * 
	 * @param pessoaDTO
	 * @throws NegocioException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void cadastraUsuario(Usuario usuario) throws NegocioException, SQLException, ParseException {

		try {
			usuarioDAO.cadastrarUsuario(usuario);
		} catch (PersistenciaException e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}

	}

	/**
	 * Lista as pessoas a partir das classes de DAO
	 * 
	 * @return
	 * @throws NegocioException
	 */
	public List<Usuario> listarUsuario() throws NegocioException   {

		List<Usuario> listUser = null;

		try {
			listUser = usuarioDAO.listarUsuarios();
		} catch (PersistenciaException | SQLException e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}

		return listUser;

	}
	/**
	 * Remove usu�rio da base
	 * @param idUsuario
	 * @throws NegocioException
	 * @throws SQLException 
	 */
	public void removerUsuario(Integer idUsuario) throws NegocioException, SQLException {
		try {

			if(validaUsuarioProjeto(idUsuario))
				usuarioDAO.removerUsuario(idUsuario);
		} catch (PersistenciaException e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}
	}

	private boolean validaUsuarioProjeto(Integer idUsuario) throws NegocioException, SQLException {
		//chama um DAO que verifica se o usuario está em algum projeto, 
		//se estiver(retorna qualquer coisa diferente de -1), retorna falso. 
		int id = -1;
		try{
			id = usuarioDAO.verificaUsuarioProjeto(idUsuario);
		} catch (PersistenciaException e){
			e.printStackTrace();
			throw new NegocioException(e);
		}
		if (id != -1) return false;
		return true;
	}
	public TipoUsuario consultaTipoUsuario(String idTipoUsuario) throws NegocioException{
		try {
			TipoUsuario tipoUsuario = usuarioDAO.consultaTipoUsuario(idTipoUsuario);
			return tipoUsuario;
		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}	
	}
	public Usuario getUsuario(int idUsuario) throws NegocioException {
		try {
			Usuario usuario = usuarioDAO.buscaUsuarioId(idUsuario);

			return usuario;
		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}	
	}

	public void editaUsuario(Usuario usuario) throws NegocioException {
		try{
			usuarioDAO.editaUsuario(usuario);
		} catch(Exception e){
			e.printStackTrace();
			throw new NegocioException(e);
		}
		
	}

}
