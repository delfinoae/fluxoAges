package br.ages.crud.bo;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import br.ages.crud.dao.PontoDAO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Ponto;
import br.ages.crud.model.ResumoPonto;
import br.ages.crud.model.StatusPonto;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.MensagemContantes;

public class PontoBO {

	public PontoBO() {
		// TODO Auto-generated constructor stub
	}

	private PontoDAO pontoDAO;

	public Boolean validaPonto(Ponto ponto) throws NegocioException, SQLException {

		try {
			if (ponto.getDataEntrada().getTime() > ponto.getDataSaida().getTime()) {
				throw new NegocioException(MensagemContantes.MSG_ERR_CADASTRO_PONTO_DATA_INVALIDA);
			} else {
				return true;
			}
		} catch (NegocioException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Cadastra Ponto do Aluno
	 * 
	 * @param Ponto
	 * @throws NegocioException
	 * @throws SQLException
	 * @throws PersistenciaException
	 * @throws ParseException
	 */
	public void cadastrarPonto(Ponto ponto) throws NegocioException, SQLException, PersistenciaException {
		pontoDAO = new PontoDAO();
		try {
			pontoDAO.cadastrarPonto(ponto);
		} catch (NegocioException e) {
			e.printStackTrace();
			throw new NegocioException(MensagemContantes.MSG_ERR_CADASTRO_PONTO);
		}
	}

	public StatusPonto validaStatusPonto(Usuario responsavel, String senhaResponsavel) throws PersistenciaException {
		UsuarioBO usuarioBO = new UsuarioBO();
		StatusPonto statusPonto;
		if (usuarioBO.validaUsuarioResponsavel(responsavel.getUsuario(), senhaResponsavel)) {
			statusPonto = StatusPonto.Valido;
		} else {
			statusPonto = StatusPonto.Invalido;
		}

		return statusPonto;
	}

	public ArrayList<ResumoPonto> listaPontoAlunos() throws NegocioException {
		pontoDAO = new PontoDAO();
		ArrayList<ResumoPonto> listaPontos = new ArrayList<>();
		try {
			listaPontos = pontoDAO.listaPontoAlunos();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return listaPontos;
	}

	public static void main(String[] args) throws NumberFormatException, NegocioException {
		PontoBO p = new PontoBO();
		int total = 0;
		for (ResumoPonto time : p.listaPontoAlunos()) {
			String[] splits = (time.getHoraEntrada().toString()).split(":");
			total += (Integer.parseInt(splits[0]) * 60 + Integer.parseInt(splits[1]));
		}

		System.out.println(total / 60 + ":" + total % 60);
	}
}