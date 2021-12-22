package br.com.alura.agenda.database;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

class AgendaMigrations {
    static final Migration[] TODAS_MIGRATIONS =
            {new Migration(1, 2) {
                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {
                    database.execSQL("ALTER TABLE aluno ADD COLUMN sobrenome TEXT");
                }
            }, new Migration(2, 3) {
                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {
                    // Criar nova tabela com as informações desejadas
                    database.execSQL("CREATE TABLE IF NOT EXISTS `Aluno_novo` " +
                            "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nome` TEXT, " +
                            "`telefone` TEXT, `email` TEXT)");

                    // Copiar dados da tabela antiga para a nova
                    database.execSQL("INSERT INTO Aluno_novo (id, nome, telefone, email) " +
                            "SELECT id, nome, telefone, email FROM Aluno");

                    // Remove tabela antiga
                    database.execSQL("DROP TABLE aluno");

                    // Renomear a tabela nova com o nome da tabela antiga
                    database.execSQL("ALTER TABLE Aluno_novo RENAME TO Aluno");
                }
            }, new Migration(3, 4) {
                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {
                    database.execSQL("ALTER TABLE Aluno ADD COLUMN momentoDeCadastro INTEGER");
                }
            }};
}
