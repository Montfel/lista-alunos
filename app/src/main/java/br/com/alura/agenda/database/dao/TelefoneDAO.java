package br.com.alura.agenda.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.alura.agenda.model.Telefone;

@Dao
public interface TelefoneDAO {
    @Query("SELECT * FROM telefone WHERE alunoId = :alunoId LIMIT 1")
    Telefone getTelefone(int alunoId);

    @Insert
    void salva(Telefone... telefones);

    @Query("SELECT * FROM telefone WHERE alunoId = :alunoId")
    List<Telefone> listAll(int alunoId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void atualiza(Telefone... telefones);
}
