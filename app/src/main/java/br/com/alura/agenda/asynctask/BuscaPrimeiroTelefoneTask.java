package br.com.alura.agenda.asynctask;

import android.os.AsyncTask;
import android.widget.TextView;

import br.com.alura.agenda.database.dao.TelefoneDAO;
import br.com.alura.agenda.model.Telefone;

public class BuscaPrimeiroTelefoneTask extends AsyncTask<Void, Void, Telefone> {

    private final TelefoneDAO dao;
    private final int alunoId;
    private final PrimeiroTelefoneEncontradoListener listener;

    public BuscaPrimeiroTelefoneTask(TelefoneDAO dao, int alunoId,
                                     PrimeiroTelefoneEncontradoListener listener) {
        this.dao = dao;
        this.alunoId = alunoId;
        this.listener = listener;
    }

    @Override
    protected Telefone doInBackground(Void... voids) {
        return dao.getTelefone(alunoId);
    }

    @Override
    protected void onPostExecute(Telefone telefone) {
        super.onPostExecute(telefone);
        listener.quandoEncontrado(telefone);
    }

    public interface PrimeiroTelefoneEncontradoListener {
        void quandoEncontrado(Telefone telefoneEncontrado);
    }
}
