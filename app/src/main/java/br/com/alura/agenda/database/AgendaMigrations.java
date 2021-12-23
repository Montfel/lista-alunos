package br.com.alura.agenda.database;

import static br.com.alura.agenda.model.TipoTelefone.FIXO;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import br.com.alura.agenda.model.TipoTelefone;

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
            }, new Migration(4, 5) {
                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {
                    database.execSQL("CREATE TABLE IF NOT EXISTS `Aluno_novo` " +
                            "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nome` TEXT, " +
                            "`telefoneFixo` TEXT, `email` TEXT, `momentoDeCadastro` INTEGER, " +
                            "`telefoneCelular` TEXT)");

                    database.execSQL("INSERT INTO Aluno_novo (id, nome, telefoneFixo, email, momentoDeCadastro) " +
                            "SELECT id, nome, telefone, email, momentoDeCadastro FROM Aluno");

                    database.execSQL("DROP TABLE aluno");

                    database.execSQL("ALTER TABLE Aluno_novo RENAME TO Aluno");
                }
            }, new Migration(5, 6) {
                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {
                    database.execSQL("CREATE TABLE IF NOT EXISTS `Aluno_novo` " +
                            "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "`nome` TEXT, `email` TEXT, `momentoDeCadastro` INTEGER)");

                    database.execSQL("INSERT INTO Aluno_novo (id, nome, email, momentoDeCadastro) " +
                            "SELECT id, nome, email, momentoDeCadastro FROM Aluno");

                    database.execSQL("CREATE TABLE IF NOT EXISTS `Telefone` " +
                            "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "`numero` TEXT, `tipo` TEXT, `alunoId` INTEGER NOT NULL, " +
                            "FOREIGN KEY(`alunoId`) REFERENCES `Aluno`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )");

                    database.execSQL("INSERT INTO Telefone (alunoId, numero) " +
                            "SELECT id, telefoneFixo FROM Aluno");

                    database.execSQL("UPDATE Telefone SET tipo = ?", new TipoTelefone[] {FIXO});

                    database.execSQL("DROP TABLE aluno");

                    database.execSQL("ALTER TABLE Aluno_novo RENAME TO Aluno");
                }
            }};
}