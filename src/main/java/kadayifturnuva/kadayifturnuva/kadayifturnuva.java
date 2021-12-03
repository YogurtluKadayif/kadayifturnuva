package kadayifturnuva.kadayifturnuva;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.BattleStartedEvent;
import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.BattleRegistry;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.battles.rules.BattleRules;
import com.pixelmonmod.pixelmon.battles.rules.clauses.BattleClause;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.enums.battle.BattleResults;
import com.pixelmonmod.pixelmon.enums.battle.EnumBattleType;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.event.message.MessageEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Plugin(
        id = "kadayifturnuva",
        name = "kadayifturnuva",
        description = "YogurtluKadayif Turnuva Plugini",
        authors = {
                "YogurtluKadayif"
        }
)
public class kadayifturnuva {

    public static String tListe1, tListe2, tListe3, tListe4, tListe5, tListe6, tListe7, vs, pl1, pl2;
    public static String turL1 = "", turL2 = "", turL3 = "", turL4 = "", turL5 = "", turL6 = "", turL7 = "";
    public static Integer esles1, esles2, klen;
    public static Integer tur = 0, vsOn = 0, tekrar = 0, tOn = 0, tKayit = 0;
    //public static Integer eslbreak = 0;
    public static String[] kt;

    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:./kadayifturnuva/db";

    static final String USER = "sa";
    static final String PASS = "";

    Connection connect = null;
    Statement statmt = null;


    @Listener
    public void preinit(GamePreInitializationEvent event) {
        Connection connect = null;
        Statement statmt = null;
        try {
            File file = new File(DB_URL);
            if (file.exists()) {
                System.out.print("Database kaydi mevcut.");
            } else {
                Class.forName(JDBC_DRIVER);
                System.out.println("Database'e baglaniliyor.");
                connect = DriverManager.getConnection(DB_URL, USER, PASS);
                statmt = connect.createStatement();
            }
            File file2 = new File("./kadayifturnuva/db.trace.db");
            if (file2.exists()) {
                file2.delete();
                System.out.println("Trace dosyasi silindi.");
            } else {
                System.out.println("Trace dosyasi bulunamadi.");
            }

            String sql = "CREATE TABLE IF NOT EXISTS   Turnuva " +
                    "(id INTEGER not NULL, " +
                    " durum INTEGER, " +
                    " tur INTEGER, " +
                    " vsdurum INTEGER, " +
                    " tekrar INTEGER, " +
                    " kayit INTEGER, " +
                    " rs1 INTEGER, " +
                    " rs2 INTEGER, " +
                    " len INTEGER, " +
                    " t1liste LONGVARCHAR, " +
                    " t2liste LONGVARCHAR, " +
                    " t3liste LONGVARCHAR, " +
                    " t4liste LONGVARCHAR, " +
                    " t5liste LONGVARCHAR, " +
                    " t6liste LONGVARCHAR, " +
                    " t7liste LONGVARCHAR, " +
                    " vs VARCHAR(255), " +
                    " player1 VARCHAR(255), " +
                    " player2 VARCHAR(255))";
            statmt.executeUpdate(sql);

            String ara = "SELECT * FROM Turnuva WHERE id='0'";
            ResultSet rsara = statmt.executeQuery(ara);
            if (rsara.next() == false) {
                String sqlturnuva = "INSERT INTO Turnuva(id, durum, tur, vsdurum, tekrar, kayit, rs1, rs2, len, t1liste, t2liste, t3liste, t4liste, t5liste, t6liste, t7liste, vs, player1, player2) VALUES" + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement preparedStatement = connect.prepareStatement(sqlturnuva);
                preparedStatement.setInt(1, 0);
                preparedStatement.setInt(2, 0);
                preparedStatement.setInt(3, 0);
                preparedStatement.setInt(4, 0);
                preparedStatement.setInt(5, 0);
                preparedStatement.setInt(6, 0);
                preparedStatement.setInt(7, 0);
                preparedStatement.setInt(8, 0);
                preparedStatement.setInt(9, 0);
                preparedStatement.setString(10, "");
                preparedStatement.setString(11, "");
                preparedStatement.setString(12, "");
                preparedStatement.setString(13, "");
                preparedStatement.setString(14, "");
                preparedStatement.setString(15, "");
                preparedStatement.setString(16, "");
                preparedStatement.setString(17, "");
                preparedStatement.setString(18, "");
                preparedStatement.setString(19, "");
                preparedStatement.executeUpdate();
            }
            System.out.println("Verilen databasede tableler olusturuldu.");
            statmt.close();
            connect.close();

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statmt != null) statmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (statmt != null) statmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    @Listener
    public void stop(GameStoppingServerEvent event) {
        try {
            if (statmt != null) statmt.close();
        } catch (SQLException se2) {
        }
        try {
            if (connect != null) connect.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    @Listener
    public void postinit(GameStartedServerEvent event) {
        Sponge.getCommandManager().register(this, komut, "yturnuva");
        Pixelmon.EVENT_BUS.register(this);
    }

    @Listener
    public void onMessage(MessageEvent e) {
        
    }

    @SubscribeEvent
    public void battleStarted(BattleStartedEvent event) {
    }
    

    @SubscribeEvent
    public void battleEnded(BattleEndEvent event) {
        if(!event.isCanceled()) {
            if(event.bc.getPlayers().size() == 2) {
                if(vsOn == 1) {
                    try {
                        connect = DriverManager.getConnection(DB_URL, USER, PASS);
                        statmt = connect.createStatement();

                        try {
                            String sqlara = "SELECT * FROM Turnuva WHERE id='1'";
                            ResultSet rsara = statmt.executeQuery(sqlara);

                            if(rsara.next()) {
                                vs = rsara.getString("vs");
                                pl1 = event.getPlayers().get(0).getName();
                                pl2 = event.getPlayers().get(1).getName();
                                if (vs.contains(pl1 + "-") || vs.contains(pl2 + "-")) {
                                    if (vs.contains("-" + pl1) || vs.contains("-" + pl2)) {
                                        if (event.results.entrySet().iterator().next().getValue() == (BattleResults.DRAW)) {
                                            if (rsara.getInt("tekrar") == 0) {
                                                Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.LIGHT_PURPLE, pl1, TextColors.GREEN, " isimli oyuncu ve ", TextColors.LIGHT_PURPLE, pl2, TextColors.GREEN, " isimli oyuncu berabere kaldi. Mac tekrar olusturuluyor. Tekrar berabere kalinirsa iki oyuncu da elenecek."));
                                                vsdurumUpdateDB(0);
                                                vsOn = 0;
                                                tekrarUpdateDB(1);
                                                turnuva();
                                            } else {
                                                if (rsara.getInt("tekrar") == 1) {
                                                    Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.LIGHT_PURPLE, pl1, TextColors.GREEN, " isimli oyuncu ve ", TextColors.LIGHT_PURPLE, pl2, TextColors.GREEN, " isimli oyuncu 2. kez berabere kaldi. Iki oyuncu da elendi."));
                                                    vsdurumUpdateDB(0);
                                                    vsOn = 0;
                                                    tekrarUpdateDB(0);
                                                    vsUpdateDB("", "", "cikar");
                                                    if (klen != 0 && klen != 1) {
                                                        Task.Builder taskBuilder = Task.builder();
                                                        taskBuilder.delay(5, TimeUnit.SECONDS).execute(() -> {
                                                            eslestir();
                                                            turnuva();
                                                        }).submit(this);

                                                    }
                                                }
                                            }
                                        } else {
                                            if (event.bc.getPlayers().get(0).isDefeated) {
                                                Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.LIGHT_PURPLE, pl2, TextColors.GREEN, " isimli oyuncu ", TextColors.LIGHT_PURPLE, pl1, TextColors.GREEN, " isimli oyuncuyu yendi. Sonraki maca geciliyor."));
                                                turYukselt(pl1);
                                                if (tur == 1) {
                                                    turGuncelle(pl1, 2, "ekle");
                                                } else {
                                                    if (tur == 2) {
                                                        turGuncelle(pl1, 3, "ekle");
                                                    } else {
                                                        if (tur == 3) {
                                                            turGuncelle(pl1, 4, "ekle");
                                                        } else {
                                                            if (tur == 4) {
                                                                turGuncelle(pl1, 5, "ekle");
                                                            } else {
                                                                if (tur == 5) {
                                                                    turGuncelle(pl1, 6, "ekle");
                                                                } else {
                                                                    if (tur == 6) {
                                                                        turGuncelle(pl1, 7, "ekle");
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                vsUpdateDB("", "", "cikar");
                                                vsdurumUpdateDB(0);
                                                tekrarUpdateDB(0);
                                                if (klen != 0 && klen != 1) {
                                                    Task.Builder taskBuilder = Task.builder();
                                                    taskBuilder.delay(5, TimeUnit.SECONDS).execute(() -> {
                                                        eslestir();
                                                        turnuva();
                                                    }).submit(this);
                                                }
                                                if (klen == 0 || klen == 1) {
                                                    tur++;
                                                    turUpdateDB(rsara.getInt("tur") + 1);
                                                    ktGuncelle(tur);
                                                    Task.Builder taskBuilder = Task.builder();
                                                    taskBuilder.delay(5, TimeUnit.SECONDS).execute(() -> {
                                                        eslestir();
                                                        turnuva();
                                                    }).submit(this);
                                                }
                                            } else {
                                                if (event.bc.getPlayers().get(1).isDefeated) {
                                                    Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.LIGHT_PURPLE, pl1, TextColors.GREEN, " isimli oyuncu ", TextColors.LIGHT_PURPLE, pl2, TextColors.GREEN, " isimli oyuncuyu yendi. Sonraki maca geciliyor."));
                                                    turYukselt(pl2);
                                                    if (tur == 1) {
                                                        turGuncelle(pl1, 2, "ekle");
                                                    } else {
                                                        if (tur == 2) {
                                                            turGuncelle(pl2, 3, "ekle");
                                                        } else {
                                                            if (tur == 3) {
                                                                turGuncelle(pl2, 4, "ekle");
                                                            } else {
                                                                if (tur == 4) {
                                                                    turGuncelle(pl2, 5, "ekle");
                                                                } else {
                                                                    if (tur == 5) {
                                                                        turGuncelle(pl2, 6, "ekle");
                                                                    } else {
                                                                        if (tur == 6) {
                                                                            turGuncelle(pl2, 7, "ekle");
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    vsUpdateDB("", "", "cikar");
                                                    vsdurumUpdateDB(0);
                                                    tekrarUpdateDB(0);
                                                    if (klen != 0 && klen != 1) {
                                                        Task.Builder taskBuilder = Task.builder();
                                                        taskBuilder.delay(5, TimeUnit.SECONDS).execute(() -> {
                                                            eslestir();
                                                            turnuva();
                                                        }).submit(this);
                                                    }
                                                    if (klen == 0 || klen == 1) {
                                                        if(tur < 6) {
                                                            Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.GREEN, "Sonraki tur basliyor."));
                                                            turUpdateDB(tur + 1);
                                                            tur++;
                                                            Task.Builder taskBuilder = Task.builder();
                                                            taskBuilder.delay(5, TimeUnit.SECONDS).execute(() -> {
                                                                eslestir();
                                                                turnuva();
                                                            }).submit(this);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (SQLException se) {
                            se.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (statmt != null) statmt.close();
                            } catch (SQLException se2) {
                            }
                            try {
                                if (connect != null) connect.close();
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }
                        }
                    } catch (SQLException se) {
                        se.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /*
        if (!event.isCanceled()) {
            if (event.bc.getPlayers().size() == 2) {
                if (vsOn == 1) {
                    //System.out.println(event.bc.getPlayers().get(0).getName());
                    if (vs.contains(event.getPlayers().get(0).getName() + "-") || vs.contains("-" + event.getPlayers().get(0).getName())) {
                        if (vs.contains(event.getPlayers().get(1).getName() + "-") || vs.contains("-" + event.getPlayers().get(1).getName())) {
                            String isim0 = event.getPlayers().get(0).getName();
                            String isim1 = event.getPlayers().get(1).getName();
                            if (event.results.entrySet().iterator().next().getValue() == (BattleResults.DRAW)) {
                                if (tekrar == 0) {
                                    Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.LIGHT_PURPLE, isim1, TextColors.GREEN, " isimli oyuncu ve ", TextColors.LIGHT_PURPLE, isim0, TextColors.GREEN, " isimli oyuncu berabere kaldi. Mac tekrar olusturuluyor."));
                                    turnuva();
                                    tekrar = 1;
                                } else {
                                    if (tekrar == 1) {
                                        Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.LIGHT_PURPLE, isim1, TextColors.GREEN, " isimli oyuncu ve ", TextColors.LIGHT_PURPLE, isim0, TextColors.GREEN, " isimli oyuncu 2. kez berabere kaldi, iki oyuncu da elendi. Sonraki maca geciliyor."));
                                        vs = null;
                                        vsOn = 0;
                                        tekrar = 0;
                                        if (klen != 0 && klen != 1) {
                                            eslestir();
                                            turnuva();
                                        }
                                    }
                                }
                            } else {
                                if (event.bc.getPlayers().get(0).isDefeated) {
                                    Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.LIGHT_PURPLE, isim1, TextColors.GREEN, " isimli oyuncu ", TextColors.LIGHT_PURPLE, isim0, TextColors.GREEN, " isimli oyuncuyu yendi. Sonraki maca geciliyor."));
                                    turYukselt(isim1);
                                    if (tur == 1) {
                                        turGuncelle(isim1, 2, "ekle");
                                    } else {
                                        if (tur == 2) {
                                            turGuncelle(isim1, 3, "ekle");
                                        } else {
                                            if (tur == 3) {
                                                turGuncelle(isim1, 4, "ekle");
                                            } else {
                                                if (tur == 4) {
                                                    turGuncelle(isim1, 5, "ekle");
                                                } else {
                                                    if (tur == 5) {
                                                        turGuncelle(isim1, 6, "ekle");
                                                    } else {
                                                        if (tur == 6) {
                                                            turGuncelle(isim1, 7, "ekle");
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    vs = null;
                                    vsOn = 0;
                                    tekrar = 0;
                                    if (klen != 0 && klen != 1) {
                                        eslestir();
                                        turnuva();
                                    }
                                    if (klen == 0 || klen == 1) {
                                        tur++;
                                    }
                                } else {
                                    if (event.bc.getPlayers().get(1).isDefeated) {
                                        Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.LIGHT_PURPLE, isim0, TextColors.GREEN, " isimli oyuncu ", TextColors.LIGHT_PURPLE, isim1, TextColors.GREEN, " isimli oyuncuyu yendi. Sonraki maca geciliyor."));
                                        turYukselt(isim0);
                                        if (tur == 1) {
                                            turGuncelle(isim0, 2, "ekle");
                                        } else {
                                            if (tur == 2) {
                                                turGuncelle(isim0, 3, "ekle");
                                            } else {
                                                if (tur == 3) {
                                                    turGuncelle(isim0, 4, "ekle");
                                                } else {
                                                    if (tur == 4) {
                                                        turGuncelle(isim0, 5, "ekle");
                                                    } else {
                                                        if (tur == 5) {
                                                            turGuncelle(isim0, 6, "ekle");
                                                        } else {
                                                            if (tur == 6) {
                                                                turGuncelle(isim0, 7, "ekle");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        vs = null;
                                        vsOn = 0;
                                        tekrar = 0;
                                        if (klen != 0 && klen != 1) {
                                            eslestir();
                                            turnuva();
                                        }
                                        if (klen == 0 || klen == 1) {
                                            tur++;
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        System.out.println(event.getPlayers().get(0).getName());
                    }
                }
            }
        }*/
    }

    public void turYukselt(String isim) {
        if(tur == 1) {
            if(tListe2 == null) {
                tListe2 = isim + ",";
            } else {
                if(tListe2 != null) {
                    tListe2 = tListe2 + isim + ",";
                }
            }
        } else {
            if(tur == 2) {
                if(tListe3 == null) {
                    tListe3 = isim + ",";
                } else {
                    if(tListe3 != null) {
                        tListe3 = tListe3 + isim + ",";
                    }
                }
            } else {
                if(tur == 3) {
                    if(tListe4 == null) {
                        tListe4 = isim + ",";
                    } else {
                        if(tListe4 != null) {
                            tListe4 = tListe4 + isim + ",";
                        }
                    }
                } else {
                    if(tur == 4) {
                        if(tListe5 == null) {
                            tListe5 = isim + ",";
                        } else {
                            if(tListe5 != null) {
                                tListe5 = tListe5 + isim + ",";
                            }
                        }
                    } else {
                        if(tur == 5) {
                            if(tListe6 == null) {
                                tListe6 = isim + ",";
                            } else {
                                if(tListe6 != null) {
                                    tListe6 = tListe6 + isim + ",";
                                }
                            }
                        } else {
                            if(tur == 6) {
                                if(tListe7 == null) {
                                    tListe7 = isim + ",";
                                } else {
                                    if(tListe7 != null) {
                                        tListe7 = tListe7 + isim + ",";
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void randomSira() {
        try {
            connect = DriverManager.getConnection(DB_URL, USER, PASS);
            statmt = connect.createStatement();

            try {
                String sqlara = "SELECT * FROM Turnuva WHERE id='1'";
                ResultSet rsara = statmt.executeQuery(sqlara);

                if(rsara.next()) {
                    tur = rsara.getInt("tur");
                    ktGuncelle(tur);

                    /*if (klen == 1 || klen == 0) {
                        tur++;
                        turUpdateDB(tur);
                        ktGuncelle(tur);
                        Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.GREEN, "Sonraki tura geciliyor." ));
                    }*/

                    int random1, random2;
                    random1 = rsara.getInt("rs1");
                    random2 = rsara.getInt("rs2");
                    //int i = 0;
                    int rdm;
                    Random random = new Random();
                    rdm = random.nextInt(klen);
                    //rdm = ThreadLocalRandom.current().nextInt(0, klen);
                    while (rdm > klen) {
                        rdm = random.nextInt(klen);
                        //rdm = ThreadLocalRandom.current().nextInt(0, klen);
                        if (rdm < klen) {
                            break;
                        }
                    }
                    esles1 = -1;
                    esles2 = -1;
                    int s = 0;
                    for (int i = 0; i < 2; i++) {
                        if (rdm < klen) {
                            s++;
                            if (s == 1) {
                                if (esles1 == -1) {
                                    if (esles2 != rdm) {
                                        rsUpdateDB(rdm, 1);
                                        esles1 = rdm;
                                        ktGuncelle(tur);
                                        random1 = rsara.getInt("rs1");
                                        System.out.println("esles1-1: " + rdm);
                                        rdm = random.nextInt(klen);
                                    } else {
                                        if (rdm - 1 > -1) {
                                            rsUpdateDB(rdm - 1, 1);
                                            esles1 = rdm - 1;
                                            ktGuncelle(tur);
                                            System.out.println("esles1-2: " + (rdm - 1));
                                            rdm = random.nextInt(klen);
                                        } else {
                                            if (rdm + 1 < klen) {
                                                rsUpdateDB(rdm + 1, 1);
                                                esles1 = rdm + 1;
                                                ktGuncelle(tur);
                                                System.out.println("esles1-3: " + (rdm + 1));
                                                rdm = random.nextInt(klen);
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (s == 2) {
                                    if (esles2 == -1) {
                                        if (esles1 != rdm) {
                                            rsUpdateDB(rdm, 2);
                                            esles2 = rdm;
                                            ktGuncelle(tur);
                                            System.out.println("esles2-1: " + rdm);
                                            rdm = random.nextInt(klen);
                                        } else {
                                            if (rdm - 1 > -1) {
                                                rsUpdateDB(rdm - 1, 2);
                                                esles2 = rdm - 1;
                                                ktGuncelle(tur);
                                                System.out.println("esles2-2: " + (rdm - 1));
                                                rdm = random.nextInt(klen);
                                            } else {
                                                if (rdm + 1 < klen) {
                                                    rsUpdateDB(rdm + 1, 2);
                                                    esles2 = rdm + 1;
                                                    ktGuncelle(tur);
                                                    System.out.println("esles2-3: " + (rdm + 1));
                                                    rdm = random.nextInt(klen);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            rdm = random.nextInt(klen);
                            i--;
                            System.out.println("else rdm<klen");
                        }
                    }
                    /*while(i < 2) {
                        i++;
                        if(rdm < klen) {
                            random1 = rsara.getInt("rs1");
                            random2 = rsara.getInt("rs2");
                            while (random1 == 0) {
                                if (random1 == 0) {
                                    if (random2 == rdm) {
                                        randomSira();
                                        break;
                                    } else {
                                        rsUpdateDB(rdm,1);
                                        esles1 = rdm;
                                        break;
                                    }
                                } else {
                                    break;
                                }
                            }
                            random1 = rsara.getInt("rs1");
                            random2 = rsara.getInt("rs2");
                            while(random2 == 0) {
                                if(random2 == 0) {
                                    if(random1 == rdm) {
                                        randomSira();
                                        break;
                                    } else {
                                        rsUpdateDB(rdm, 2);
                                        esles2 = rdm;
                                        break;
                                    }
                                } else {
                                    break;
                                }
                            }
                        } else {
                            randomSira();
                        }
                    }*/

                }


            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (statmt != null) statmt.close();
                } catch (SQLException se2) {
                }
                try {
                    if (connect != null) connect.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
        int i = 0;
        int s;
        if(tur == 1) {
            ktGuncelle(1);
        } else {
            if(tur == 2) {
                ktGuncelle(2);
            } else {
                if(tur == 3) {
                    ktGuncelle(3);
                } else {
                    if(tur == 4) {
                        ktGuncelle(4);
                    } else {
                        if(tur == 5) {
                            ktGuncelle(5);
                        } else {
                            if(tur == 6) {
                                ktGuncelle(6);
                            } else {
                                if(tur == 7) {
                                    ktGuncelle(7);
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println(klen + " " + Arrays.toString(kt));

        //Random random = new Random();
        s = ThreadLocalRandom.current().nextInt(0, klen);
        while(s > klen) {
            s = ThreadLocalRandom.current().nextInt(0, klen);
            if(s < klen) {
                break;
            }
        }
        while(i < 2) {
            i++;
            //i = random.nextInt(klen+1);

            if (s < klen) {
                while (esles1 == null) {
                    if (esles1 == null) {
                        if (esles2 != null && esles2 == s) {
                            s = ThreadLocalRandom.current().nextInt(0, klen);
                        } else {
                            esles1 = s;
                            break;
                        }
                    } else {
                        break;
                    }
                }
                while (esles2 == null) {
                    if (esles2 == null) {
                        if (esles1 != null && esles1 == s) {
                            s = ThreadLocalRandom.current().nextInt(0, klen);
                        } else {
                            esles2 = s;
                            break;
                        }
                    } else {
                        break;
                    }
                }
            } else {
                randomSira();
            }
        }*/
    }

    public void eslestir() {
        try {
            connect = DriverManager.getConnection(DB_URL, USER, PASS);
            statmt = connect.createStatement();

            try {
                String sqlara = "SELECT * FROM Turnuva WHERE id='1'";
                ResultSet rsara = statmt.executeQuery(sqlara);

                if(rsara.next()) {
                    randomSira();
                    //int rs1=0, rs2=0;
                    int rs1 = (int)rsara.getDouble("rs1");
                    int rs2 = (int)rsara.getDouble("rs2");
                    System.out.println("rs1:" + rs1 + " rs2:" +rs2);
                    ktGuncelle(tur);
                    if(rs1 < klen && rs2 < klen) {
                        //while(kt[rs1] != null && kt[rs2] != null) {
                        if(kt[esles1] != null && kt[esles2] != null) {//rs1 - rs2
                            vsUpdateDB(kt[esles1], kt[esles2], "ekle");//rs1 - rs2
                            vs = kt[esles1] + "-" + kt[esles2];//rs1 - rs2
                            System.out.println("kt: " + Arrays.toString(kt) + "kt1: " + kt[rs1] + " kt2: " + kt[rs2] + " vs: " + vs);

                            pl1 = kt[esles1];//rs1
                            pl2 = kt[esles2];//rs2
                            turGuncelle(pl1, 1, "cikar");
                            turGuncelle(pl2, 1, "cikar");
                            ktGuncelle(tur);

                            if ((klen - 2) != 0 && (klen - 2) != 1) {
                                eslestir();
                                turnuva();
                            }
                        }
                        //}
                    } else {
                        System.out.println("else loop");
                    }
                }
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (statmt != null) statmt.close();
                } catch (SQLException se2) {
                }
                try {
                    if (connect != null) connect.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*
        randomSira();
        if(esles1 < klen && esles2 < klen) {
            while (kt[esles1] != null && kt[esles2] != null) {
                if (!kt[esles1].contains("eslesti") && !kt[esles2].contains("eslesti")) {
                    System.out.println(kt[esles1] + " vs " + kt[esles2]);
                    vs = kt[esles1] + "-" + kt[esles2];
                    pl1 = kt[esles1];
                    pl2 = kt[esles2];
                    //Array.set(kt, esles1, "eslesti");
                    //Array.set(kt, esles2, "eslesti");
                    System.out.println(kt);
                    turGuncelle(pl1, 1, 2);
                    turGuncelle(pl2, 1, 2);
                    ktGuncelle(1);
                    System.out.println(kt);

                    if (klen - 2 != 0 && klen - 2 != 1) {
                        //randomSira();
                        //eslestir();
                        //turnuva();
                        System.out.println("1");
                        break;
                    } else {
                        break;
                    }
                } else {
                    if (klen - 2 != 0 && klen - 2 != 1) {
                        randomSira();
                        eslestir();
                        turnuva();
                        System.out.println("2");
                        break;
                    } else {
                        System.out.println("eslestir break");
                        break;
                    }
                }
            }
        } else {
            eslestir();
        } */
    }

    public BattleParticipant katilimci(int ps, Player p) {
        EntityPlayerMP player = (EntityPlayerMP)p;
        PlayerPartyStorage party = Pixelmon.storageManager.getParty(player);
        List<Pokemon> pokemon = party.findAll((pk) -> {
            return pk.getHealth() > 0 && !pk.isEgg();
        });
        EntityPixelmon[] pixelmons = new EntityPixelmon[ps];
        for(int i = 0; i < pixelmons.length; ++i) {
            pixelmons[i] = ((Pokemon)pokemon.get(i)).getOrSpawnPixelmon(player);
        }
        return new PlayerParticipant(player, pixelmons);

    }

    public void turnuva() {
        try {
            connect = DriverManager.getConnection(DB_URL, USER, PASS);
            statmt = connect.createStatement();

            try {
                String sqlara = "SELECT * FROM Turnuva WHERE id='1'";
                ResultSet rsara = statmt.executeQuery(sqlara);

                if(rsara.next()) {
                    Task.Builder taskBuilder = Task.builder();
                    int p1on, p2on;
                    if(!rsara.getString("vs").equals("")) {
                        String[] vsr = rsara.getString("vs").split("-");
                        pl1 = vsr[0];
                        pl2 = vsr[1];
                        Player player1 = null, player2 = null;
                        if(Sponge.getServer().getPlayer(pl1).isPresent()) {
                            player1 = Sponge.getServer().getPlayer(pl1).get();
                            p1on = 1;
                        } else {
                            p1on = 0;
                        }
                        if(Sponge.getServer().getPlayer(pl2).isPresent()) {
                            player2= Sponge.getServer().getPlayer(pl2).get();
                            p2on = 1;
                        } else {
                            p2on = 0;
                        }
                        if(p1on == 0 && p2on == 0) {
                            Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.LIGHT_PURPLE, pl1, TextColors.GREEN, " ve ", TextColors.LIGHT_PURPLE, pl2, TextColors.GREEN, " isimli oyuncular oyunda olmadigi icin ikisi de elendi." ));
                            vsUpdateDB("","","cikar");
                            if(klen >= 2) {
                                eslestir();
                                turnuva();
                            }
                        } else {
                            if(p1on == 1 && p2on == 0) {
                                Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.LIGHT_PURPLE, pl1, TextColors.GREEN, " isimli oyuncu ", TextColors.LIGHT_PURPLE, pl2, TextColors.GREEN, " isimli oyuncu oyunda olmadigi icin sonraki tura cikti." ));
                                turYukselt(pl1);
                                turGuncelle(pl1,tur+1,"ekle");
                                vsUpdateDB("","","cikar");
                                if(klen >= 2) {
                                    eslestir();
                                    turnuva();
                                }
                            } else {
                                if(p1on == 0 && p2on == 1) {
                                    Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.LIGHT_PURPLE, pl2, TextColors.GREEN, " isimli oyuncu ", TextColors.LIGHT_PURPLE, pl1, TextColors.GREEN, " isimli oyuncu oyunda olmadigi icin sonraki tura cikti." ));
                                    turYukselt(pl2);
                                    turGuncelle(pl2,tur+1,"ekle");
                                    vsUpdateDB("","","cikar");
                                    if(klen >= 2) {
                                        eslestir();
                                        turnuva();
                                    }
                                } else {
                                    if(p1on == 1 && p2on == 1 && rsara.getInt("vsdurum") == 0) {
                                        vsdurumUpdateDB(1);
                                        vsOn = 1;

                                        BattleRules br = new BattleRules();
                                        br.fullHeal = true;
                                        br.levelCap = 100;
                                        br.numPokemon = 6;
                                        br.raiseToCap = true;
                                        br.turnTime = 30;
                                        br.battleType = EnumBattleType.Single;
                                        BattleClause bag = new BattleClause("bag");
                                        BattleClause batonpass = new BattleClause("batonpass");
                                        BattleClause item = new BattleClause("item");
                                        BattleClause ohko = new BattleClause("ohko");
                                        BattleClause sleep = new BattleClause("sleep");
                                        List<BattleClause> bc = new ArrayList<BattleClause>();
                                        bc.add(bag);
                                        bc.add(batonpass);
                                        bc.add(item);
                                        bc.add(ohko);
                                        bc.add(sleep);
                                        br.setNewClauses(bc);

                                        BattleParticipant bir = this.katilimci(br.battleType.numPokemon, player1);
                                        BattleParticipant iki = this.katilimci(br.battleType.numPokemon, player2);
                                        player1.sendMessage(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.GREEN, "5 saniye icinde maciniz basliyor. Rakibiniz: ", TextColors.LIGHT_PURPLE, pl2));
                                        player2.sendMessage(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.GREEN, "5 saniye icinde maciniz basliyor. Rakibiniz: ", TextColors.LIGHT_PURPLE, pl1));
                                        taskBuilder.delay(5, TimeUnit.SECONDS).execute(() -> {
                                            BattleRegistry.startBattle(new BattleParticipant[]{bir}, new BattleParticipant[]{iki}, br);
                                            Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.LIGHT_PURPLE, pl1, TextColors.GREEN, " ve ", TextColors.LIGHT_PURPLE, pl2, TextColors.GREEN, " arasindaki mac basladi."));
                                        }).submit(this);
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (statmt != null) statmt.close();
                } catch (SQLException se2) {
                }
                try {
                    if (connect != null) connect.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*
        String k1,k2;
        String[] katilimcilar;
        int kt1on, kt2on;
        Task.Builder taskBuilder = Task.builder();
        if(vs != null) {
            katilimcilar = vs.split("-");
            k1 = katilimcilar[0];
            k2 = katilimcilar[1];
            Player kt1 = null, kt2 = null;
            //tur++;
            // Online Kontrol -------
            if(Sponge.getServer().getPlayer(k1).isPresent()) {
                kt1 = Sponge.getServer().getPlayer(k1).get();
                kt1on = 1;
            } else {
                //Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.LIGHT_PURPLE, k1, TextColors.GREEN, " isimli oyuncu oyunda olmadigi icin ", TextColors.LIGHT_PURPLE, k2, TextColors.GREEN, " isimli oyuncu sonraki tura gecti." ));
                kt1on = 0;
                //turYukselt(k2);
                /*if(tur == 1) {
                    if(tListe2 == null) {
                        tListe2 = k2 + ",";
                    } else {
                        if(tListe2 != null) {
                            tListe2 = tListe2 + k2 + ",";
                        }
                    }
                } else {
                    if(tur == 2) {
                        if(tListe3 == null) {
                            tListe3 = k2 + ",";
                        } else {
                            if(tListe3 != null) {
                                tListe3 = tListe3 + k2 + ",";
                            }
                        }
                    } else {
                        if(tur == 3) {
                            if(tListe4 == null) {
                                tListe4 = k2 + ",";
                            } else {
                                if(tListe4 != null) {
                                    tListe4 = tListe4 + k2 + ",";
                                }
                            }
                        } else {
                            if(tur == 4) {
                                if(tListe5 == null) {
                                    tListe5 = k2 + ",";
                                } else {
                                    if(tListe5 != null) {
                                        tListe5 = tListe5 + k2 + ",";
                                    }
                                }
                            } else {
                                if(tur == 5) {
                                    if(tListe6 == null) {
                                        tListe6 = k2 + ",";
                                    } else {
                                        if(tListe6 != null) {
                                            tListe6 = tListe6 + k2 + ",";
                                        }
                                    }
                                } else {
                                    if(tur == 6) {
                                        if(tListe7 == null) {
                                            tListe7 = k2 + ",";
                                        } else {
                                            if(tListe7 != null) {
                                                tListe7 = tListe7 + k2 + ",";
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if(Sponge.getServer().getPlayer(k2).isPresent()) {
                kt2 = Sponge.getServer().getPlayer(k2).get();
                kt2on = 1;
            } else {
                //Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.LIGHT_PURPLE, k2, TextColors.GREEN, " isimli oyuncu oyunda olmadigi icin ", TextColors.LIGHT_PURPLE, k1, TextColors.GREEN, " isimli oyuncu sonraki tura gecti." ));
                kt2on = 0;
                //turYukselt(k1);
                /*if(tur == 1) {
                    if(tListe2 == null) {
                        tListe2 = k1 + ",";
                    } else {
                        if(tListe2 != null) {
                            tListe2 = tListe2 + k1 + ",";
                        }
                    }
                } else {
                    if(tur == 2) {
                        if(tListe3 == null) {
                            tListe3 = k1 + ",";
                        } else {
                            if(tListe3 != null) {
                                tListe3 = tListe3 + k1 + ",";
                            }
                        }
                    } else {
                        if(tur == 3) {
                            if(tListe4 == null) {
                                tListe4 = k1 + ",";
                            } else {
                                if(tListe4 != null) {
                                    tListe4 = tListe4 + k1 + ",";
                                }
                            }
                        } else {
                            if(tur == 4) {
                                if(tListe5 == null) {
                                    tListe5 = k1 + ",";
                                } else {
                                    if(tListe5 != null) {
                                        tListe5 = tListe5 + k1 + ",";
                                    }
                                }
                            } else {
                                if(tur == 5) {
                                    if(tListe6 == null) {
                                        tListe6 = k1 + ",";
                                    } else {
                                        if(tListe6 != null) {
                                            tListe6 = tListe6 + k1 + ",";
                                        }
                                    }
                                } else {
                                    if(tur == 6) {
                                        if(tListe7 == null) {
                                            tListe7 = k1 + ",";
                                        } else {
                                            if(tListe7 != null) {
                                                tListe7 = tListe7 + k1 + ",";
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }*/
            /*}
            if((kt1on == 1 && kt2on == 0) || (kt1on == 0 && kt2on == 1)) {
                if(kt1on == 1 && kt2on == 0) {
                    turYukselt(k1);
                } else {
                    if(kt1on == 0 && kt2on == 1) {
                        turYukselt(k2);
                    } else {
                        if(kt1on == 0 && kt2on == 0) {
                            Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.LIGHT_PURPLE, k1, TextColors.GREEN, " ve ", TextColors.LIGHT_PURPLE, k2, TextColors.GREEN, " isimli oyuncular oyunda olmadigi icin ikisi de elendi." ));
                            vs = null;
                        }
                    }
                }
            }
            // -------

            if(kt1on == 1 && kt2on == 1 && vsOn == 0) {
                BattleRules br = new BattleRules();
                br.fullHeal = true;
                br.levelCap = 100;
                br.numPokemon = 6;
                br.raiseToCap = true;
                br.turnTime = 30;
                br.battleType = EnumBattleType.Single;
                BattleClause bag = new BattleClause("bag");
                BattleClause batonpass = new BattleClause("batonpass");
                BattleClause item = new BattleClause("item");
                BattleClause ohko = new BattleClause("ohko");
                BattleClause sleep = new BattleClause("sleep");
                List<BattleClause> bc = new ArrayList<BattleClause>();
                bc.add(bag);
                bc.add(batonpass);
                bc.add(item);
                bc.add(ohko);
                bc.add(sleep);
                br.setNewClauses(bc);

                BattleParticipant bir = this.katilimci(br.battleType.numPokemon, kt1);
                BattleParticipant iki = this.katilimci(br.battleType.numPokemon, kt2);
                kt1.sendMessage(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.GREEN, "5 saniye icinde maciniz basliyor. Rakibiniz: ", TextColors.LIGHT_PURPLE, k2));
                kt2.sendMessage(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.GREEN, "5 saniye icinde maciniz basliyor. Rakibiniz: ", TextColors.LIGHT_PURPLE, k1));
                taskBuilder.delay(5, TimeUnit.SECONDS).execute(() -> {
                    BattleRegistry.startBattle(new BattleParticipant[]{bir}, new BattleParticipant[]{iki}, br);
                    Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.LIGHT_PURPLE, k1, TextColors.GREEN, " ve ", TextColors.LIGHT_PURPLE, k2, TextColors.GREEN, " arasindaki mac basladi."));
                }).submit(this);

                vsOn = 1;
            }
        }*/
    }

    public void ktGuncelle(int liste) {
        try {
            connect = DriverManager.getConnection(DB_URL, USER, PASS);
            statmt = connect.createStatement();

            try {
                String sqlara = "SELECT * FROM Turnuva WHERE id='1'";
                ResultSet rsara = statmt.executeQuery(sqlara);
                String rs;
                if(rsara.next()) {
                    if(liste == 1) {
                        rs = rsara.getString("t1liste");
                        kt = rs.split(",");
                        lenUpdateDB(kt.length);
                        klen = kt.length;
                    } else {
                        if(liste == 2) {
                            rs = rsara.getString("t2liste");
                            kt = rs.split(",");
                            lenUpdateDB(kt.length);
                            klen = kt.length;
                        } else {
                            if(liste == 3) {
                                rs = rsara.getString("t3liste");
                                kt = rs.split(",");
                                lenUpdateDB(kt.length);
                                klen = kt.length;
                            } else {
                                if(liste == 4) {
                                    rs = rsara.getString("t4liste");
                                    kt = rs.split(",");
                                    lenUpdateDB(kt.length);
                                    klen = kt.length;
                                } else {
                                    if(liste == 5) {
                                        rs = rsara.getString("t5liste");
                                        kt = rs.split(",");
                                        lenUpdateDB(kt.length);
                                        klen = kt.length;
                                    } else {
                                        if(liste == 6) {
                                            rs = rsara.getString("t6liste");
                                            kt = rs.split(",");
                                            lenUpdateDB(kt.length);
                                            klen = kt.length;
                                        } else {
                                            if(liste == 7) {
                                                rs = rsara.getString("t7liste");
                                                kt = rs.split(",");
                                                lenUpdateDB(kt.length);
                                                klen = kt.length;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (statmt != null) statmt.close();
                } catch (SQLException se2) {
                }
                try {
                    if (connect != null) connect.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
        //turL1 = turL1.substring(0, turL1.length() - 1);
        if (liste == 1) {
            //StringBuilder builder = new StringBuilder(turL1);
            //turL1 = StringUtils.chop(turL1);
            //builder.deleteCharAt(turL1.length() - 1);
            kt = turL1.split(",");
            klen = kt.length;
        } else {
            if (liste == 2) {
                kt = turL2.split(",");
                klen = kt.length;
            } else {
                if (liste == 3) {
                    kt = turL3.split(",");
                    klen = kt.length;
                } else {
                    if (liste == 4) {
                        kt = turL4.split(",");
                        klen = kt.length;
                    } else {
                        if (liste == 5) {
                            kt = turL5.split(",");
                            klen = kt.length;
                        } else {
                            if (liste == 6) {
                                kt = turL6.split(",");
                                klen = kt.length;
                            } else {
                                if (liste == 7) {
                                    kt = turL7.split(",");
                                    klen = kt.length;
                                }
                            }
                        }
                    }
                }
            }
        }*/
    }

    public void turGuncelle(String isim, int liste, String y) {
        // y == ekle turL'ye ekle || y == cikar turL'den çıkar
        try {
            connect = DriverManager.getConnection(DB_URL, USER, PASS);
            statmt = connect.createStatement();

            try {
                String sqlara = "SELECT * FROM Turnuva WHERE id='1'";
                ResultSet rsara = statmt.executeQuery(sqlara);

                if(rsara.next()) {
                    String tlist;
                    if(liste == 1) {
                        tlist = rsara.getString("t1liste");
                        if(y.equals("ekle") && !tlist.contains(isim + ",")) {
                            tlist = tlist + isim + ",";
                            listeUpdateDB(tlist, 1);
                        } else {
                            if(y.equals("cikar") && tlist.contains(isim + ",")) {
                                tlist = tlist.replaceAll(isim + ",", "");
                                listeUpdateDB(tlist, 1);
                            }
                        }
                    } else {
                        if(liste == 2) {
                            tlist = rsara.getString("t2liste");
                            if(y.equals("ekle") && !tlist.contains(isim + ",")) {
                                tlist = tlist + isim + ",";
                                listeUpdateDB(tlist, 2);
                            } else {
                                if(y.equals("cikar") && tlist.contains(isim + ",")) {
                                    tlist = tlist.replaceAll(isim + ",", "");
                                    listeUpdateDB(tlist, 2);
                                }
                            }
                        } else {
                            if(liste == 3) {
                                tlist = rsara.getString("t3liste");
                                if(y.equals("ekle") && !tlist.contains(isim + ",")) {
                                    tlist = tlist + isim + ",";
                                    listeUpdateDB(tlist, 3);
                                } else {
                                    if(y.equals("cikar") && tlist.contains(isim + ",")) {
                                        tlist = tlist.replaceAll(isim + ",", "");
                                        listeUpdateDB(tlist, 3);
                                    }
                                }
                            } else {
                                if(liste == 4) {
                                    tlist = rsara.getString("t4liste");
                                    if(y.equals("ekle") && !tlist.contains(isim + ",")) {
                                        tlist = tlist + isim + ",";
                                        listeUpdateDB(tlist, 4);
                                    } else {
                                        if(y.equals("cikar") && tlist.contains(isim + ",")) {
                                            tlist = tlist.replaceAll(isim + ",", "");
                                            listeUpdateDB(tlist, 4);
                                        }
                                    }
                                } else {
                                    if(liste == 5) {
                                        tlist = rsara.getString("t5liste");
                                        if(y.equals("ekle") && !tlist.contains(isim + ",")) {
                                            tlist = tlist + isim + ",";
                                            listeUpdateDB(tlist, 5);
                                        } else {
                                            if(y.equals("cikar") && tlist.contains(isim + ",")) {
                                                tlist = tlist.replaceAll(isim + ",", "");
                                                listeUpdateDB(tlist, 5);
                                            }
                                        }
                                    } else {
                                        if(liste == 6) {
                                            tlist = rsara.getString("t6liste");
                                            if(y.equals("ekle") && !tlist.contains(isim + ",")) {
                                                tlist = tlist + isim + ",";
                                                listeUpdateDB(tlist, 6);
                                            } else {
                                                if(y.equals("cikar") && tlist.contains(isim + ",")) {
                                                    tlist = tlist.replaceAll(isim + ",", "");
                                                    listeUpdateDB(tlist, 6);
                                                }
                                            }
                                        } else {
                                            if(liste == 7) {
                                                tlist = rsara.getString("t7liste");
                                                if(y.equals("ekle") && !tlist.contains(isim + ",")) {
                                                    tlist = tlist + isim + ",";
                                                    listeUpdateDB(tlist, 7);
                                                } else {
                                                    if(y.equals("cikar") && tlist.contains(isim + ",")) {
                                                        tlist = tlist.replaceAll(isim + ",", "");
                                                        listeUpdateDB(tlist, 7);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (statmt != null) statmt.close();
                } catch (SQLException se2) {
                }
                try {
                    if (connect != null) connect.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
        if(liste == 1) {
            if(y == 1 && !turL1.contains(isim + ",")) {
                turL1 = turL1 + isim + ",";
            } else {
                if(y == 2 && turL1.contains(isim + ",")) {
                    turL1 = turL1.replaceAll(isim + ",", "");
                }
            }
        } else {
            if(liste == 2) {
                if(y == 1 && !turL2.contains(isim + ",")) {
                    turL2 = turL2 + isim + ",";
                } else {
                    if(y == 2 && turL2.contains(isim + ",")) {
                        turL2 = turL2.replaceAll(isim + ",", "");
                    }
                }
            } else {
                if(liste == 3) {
                    if(y == 1 && !turL3.contains(isim + ",")) {
                        turL3 = turL3 + isim + ",";
                    } else {
                        if(y == 2 && turL3.contains(isim + ",")) {
                            turL3.replaceAll(isim + ",", "");
                        }
                    }
                } else {
                    if(liste == 4) {
                        if(y == 1 && !turL4.contains(isim + ",")) {
                            turL4 = turL4 + isim + ",";
                        } else {
                            if(y == 2 && turL4.contains(isim + ",")) {
                                turL4.replaceAll(isim + ",", "");
                            }
                        }
                    } else {
                        if(liste == 5) {
                            if(y == 1 && !turL5.contains(isim + ",")) {
                                turL5 = turL5 + isim + ",";
                            } else {
                                if(y == 2 && turL5.contains(isim + ",")) {
                                    turL5.replaceAll(isim + ",", "");
                                }
                            }
                        } else {
                            if(liste == 6) {
                                if(y == 1 && !turL6.contains(isim + ",")) {
                                    turL6 = turL6 + isim + ",";
                                } else {
                                    if(y == 2 && turL6.contains(isim + ",")) {
                                        turL6.replaceAll(isim + ",", "");
                                    }
                                }
                            } else {
                                if(liste == 7) {
                                    if(y == 1 && !turL7.contains(isim + ",")) {
                                        turL7 = turL7 + isim + ",";
                                    } else {
                                        if(y == 2 && turL7.contains(isim + ",")) {
                                            turL7.replaceAll(isim + ",", "");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }*/
    }


    public void sonrakiTur() {
        try {
            connect = DriverManager.getConnection(DB_URL, USER, PASS);
            statmt = connect.createStatement();

            try {
                String sqlara = "SELECT * FROM Turnuva WHERE id='1'";
                ResultSet rsara = statmt.executeQuery(sqlara);

                if(rsara.next()) {
                    if(rsara.getString("t"+tur+"liste").equals("")) {
                        tur++;
                        turUpdateDB(tur);
                        ktGuncelle(tur);
                    }
                }
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (statmt != null) statmt.close();
                } catch (SQLException se2) {
                }
                try {
                    if (connect != null) connect.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Önemli
    public void turUpdateDB(int d) {
        try {
            connect = DriverManager.getConnection(DB_URL, USER, PASS);
            statmt = connect.createStatement();

            try {
                String sqlara = "SELECT * FROM Turnuva WHERE id='1'";
                ResultSet rsara = statmt.executeQuery(sqlara);

                if(rsara.next()) {
                    String sql = "UPDATE Turnuva SET tur=? WHERE id='1'";
                    PreparedStatement ps = connect.prepareStatement(sql);
                    ps.setInt(1, d);
                    ps.executeUpdate();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (statmt != null) statmt.close();
                } catch (SQLException se2) {
                }
                try {
                    if (connect != null) connect.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Önemli
    public void tekrarUpdateDB(int d) {
        try {
            connect = DriverManager.getConnection(DB_URL, USER, PASS);
            statmt = connect.createStatement();

            try {
                String sqlara = "SELECT * FROM Turnuva WHERE id='1'";
                ResultSet rsara = statmt.executeQuery(sqlara);

                if(rsara.next()) {
                    String sql = "UPDATE Turnuva SET tekrar=? WHERE id='1'";
                    PreparedStatement ps = connect.prepareStatement(sql);
                    ps.setInt(1, d);
                    ps.executeUpdate();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (statmt != null) statmt.close();
                } catch (SQLException se2) {
                }
                try {
                    if (connect != null) connect.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Önemli
    public void vsdurumUpdateDB(int d) {
        try {
            connect = DriverManager.getConnection(DB_URL, USER, PASS);
            statmt = connect.createStatement();

            try {
                String sqlara = "SELECT * FROM Turnuva WHERE id='1'";
                ResultSet rsara = statmt.executeQuery(sqlara);

                if(rsara.next()) {
                    String sql = "UPDATE Turnuva SET vsdurum=? WHERE id='1'";
                    PreparedStatement ps = connect.prepareStatement(sql);
                    ps.setInt(1, d);
                    ps.executeUpdate();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (statmt != null) statmt.close();
                } catch (SQLException se2) {
                }
                try {
                    if (connect != null) connect.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Önemli
    public void listeUpdateDB(String liste, int s) {
        try {
            connect = DriverManager.getConnection(DB_URL, USER, PASS);
            statmt = connect.createStatement();

            try {
                String sqlara = "SELECT * FROM Turnuva WHERE id='1'";
                ResultSet rsara = statmt.executeQuery(sqlara);

                if(rsara.next()) {
                    String sql = "UPDATE Turnuva SET t" + s + "liste=? WHERE id='1'";
                    PreparedStatement ps = connect.prepareStatement(sql);
                    ps.setString(1, liste);
                    ps.executeUpdate();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (statmt != null) statmt.close();
                } catch (SQLException se2) {
                }
                try {
                    if (connect != null) connect.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Önemli
    public void vsUpdateDB(String player, String player2, String y) {
        try {
            connect = DriverManager.getConnection(DB_URL, USER, PASS);
            statmt = connect.createStatement();

            try {
                String sqlara = "SELECT * FROM Turnuva WHERE id='1'";
                ResultSet rsara = statmt.executeQuery(sqlara);

                if(rsara.next()) {
                    if(y.equals("ekle")) {
                        String sql = "UPDATE Turnuva SET vs=? WHERE id='1'";
                        PreparedStatement ps = connect.prepareStatement(sql);
                        ps.setString(1, player + "-" + player2);
                        ps.executeUpdate();
                    } else {
                        if(y.equals("cikar")) {
                            String sql = "UPDATE Turnuva SET vs=? WHERE id='1'";
                            PreparedStatement ps = connect.prepareStatement(sql);
                            ps.setString(1, "");
                            ps.executeUpdate();
                        }
                    }
                }
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (statmt != null) statmt.close();
                } catch (SQLException se2) {
                }
                try {
                    if (connect != null) connect.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Önemli
    public void rsUpdateDB(int random, int w) {
        // w = 1 rs1 || w = 2 rs2
        try {
            connect = DriverManager.getConnection(DB_URL, USER, PASS);
            statmt = connect.createStatement();

            try {
                String sqlara = "SELECT * FROM Turnuva WHERE id='1'";
                ResultSet rsara = statmt.executeQuery(sqlara);

                if(rsara.next()) {
                    String sql = "UPDATE Turnuva SET rs" + w + "=? WHERE id='1'";
                    PreparedStatement ps = connect.prepareStatement(sql);
                    ps.setInt(1, random);
                    ps.executeUpdate();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (statmt != null) statmt.close();
                } catch (SQLException se2) {
                }
                try {
                    if (connect != null) connect.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Önemli
    public void lenUpdateDB(int length) {
        try {
            connect = DriverManager.getConnection(DB_URL, USER, PASS);
            statmt = connect.createStatement();

            try {
                String sqlara = "SELECT * FROM Turnuva WHERE id='1'";
                ResultSet rsara = statmt.executeQuery(sqlara);

                if(rsara.next()) {
                    String sql = "UPDATE Turnuva SET len=? WHERE id='1'";
                    PreparedStatement ps = connect.prepareStatement(sql);
                    ps.setInt(1, length);
                    ps.executeUpdate();

                    klen = length;
                }
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (statmt != null) statmt.close();
                } catch (SQLException se2) {
                }
                try {
                    if (connect != null) connect.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    CommandSpec baslat = CommandSpec.builder()
            .description(Text.of("Turnuvayi baslatmanizi saglar."))
            .permission("admin.turbaslat")
            .executor((CommandSource src, CommandContext args) -> {
                try {
                    connect = DriverManager.getConnection(DB_URL, USER, PASS);
                    statmt = connect.createStatement();

                    try {
                        String sqlara = "SELECT * FROM Turnuva WHERE id='1'";
                        ResultSet rsara = statmt.executeQuery(sqlara);

                        if(rsara.next()) {
                            if(rsara.getInt("durum") == 1) {
                                kt = rsara.getString("t1liste").split(",");
                                lenUpdateDB(kt.length);
                                klen = kt.length;
                                if (klen % 2 == 0) {
                                    tur = 1;
                                    eslestir();
                                    turnuva();
                                }
                            } else {
                                src.sendMessage(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.DARK_RED, "Suan aktif bir turnuva bulunmamaktadir."));
                            }
                        } else {
                            src.sendMessage(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.DARK_RED, "Suan olusturulmus bir turnuva bulunmamaktadir."));
                        }
                    } catch (SQLException se) {
                        se.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (statmt != null) statmt.close();
                        } catch (SQLException se2) {
                        }
                        try {
                            if (connect != null) connect.close();
                        } catch (SQLException se) {
                            se.printStackTrace();
                        }
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                /*if(tOn == 1) {
                    //kt = turL1.split(",");
                    //klen = kt.length;
                    ktGuncelle(1);
                    Random random = new Random();
                    //int i = random.nextInt(klen + 1);
                    if (klen % 2 == 0) {
                        tur = 1;
                        eslestir();
                        turnuva();

                        //tKayit = 0;
                    /*
                    randomSira(katilimlen);
                    if(!katilimcilar[esles1].equals("eslesti") && !katilimcilar[esles2].equals("eslesti")) {
                        System.out.println(katilimcilar[esles1] + " vs " + katilimcilar[esles2]);
                        Array.set(katilimcilar, esles1, "eslesti");
                        Array.set(katilimcilar, esles2, "eslesti");
                    } else {
                        randomSira(katilimlen);
                    }
                    */
                    /*} else {
                        if (klen % 2 == 1) {

                        }
                    }
                }*/

                return CommandResult.success();
            })
            .build();

    CommandSpec katil = CommandSpec.builder()
            .description(Text.of("Turnuvaya katilmanizi saglar."))
            .permission("kturnuva.katil")
            .executor((CommandSource src, CommandContext args) -> {

                try {
                    connect = DriverManager.getConnection(DB_URL, USER, PASS);
                    statmt = connect.createStatement();

                    try {
                        String sqlara = "SELECT * FROM Turnuva WHERE id='1'";
                        ResultSet rsara = statmt.executeQuery(sqlara);

                        if(rsara.next()) {
                            if (!rsara.getString("t1liste").contains(src.getName() + ",")) {
                                if(rsara.getInt("kayit") != 0) {
                                    String t1liste = rsara.getString("t1liste");
                                    String sql = "UPDATE Turnuva SET t1liste=? WHERE id='1'";
                                    PreparedStatement ps = connect.prepareStatement(sql);
                                    if (t1liste == "") {
                                        t1liste = src.getName() + ",";
                                    } else {
                                        if (!t1liste.contains(src.getName() + ",")) {
                                            t1liste = t1liste + src.getName() + ",";
                                        }
                                    }
                                    ps.setString(1, t1liste);
                                    ps.executeUpdate();

                                    src.sendMessage(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.GREEN, "Turnuvaya basariyla katildiniz."));
                                } else {
                                    src.sendMessage(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.DARK_RED, "Turnuva basladigi icin katilamazsiniz."));
                                }
                            } else {
                                src.sendMessage(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.DARK_RED, "Turnuvaya zaten katildiniz."));
                            }
                        } else {
                            src.sendMessage(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.DARK_RED, "Suan aktif bir turnuva bulunmamaktadir."));
                        }


                    } catch (SQLException se) {
                        se.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (statmt != null) statmt.close();
                        } catch (SQLException se2) {
                        }
                        try {
                            if (connect != null) connect.close();
                        } catch (SQLException se) {
                            se.printStackTrace();
                        }
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /*if(tOn != null && tOn == 1) {
                    if (tListe1 == null) {
                        tListe1 = src.getName() + ",";
                        turGuncelle(src.getName(), 1, 1);
                        src.sendMessage(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.GREEN, "Turnuvaya katildiniz."));
                    } else {
                        if (!tListe1.contains(src.getName())) {
                            tListe1 = tListe1 + src.getName() + ",";
                            turGuncelle(src.getName(),1,1);
                            src.sendMessage(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.GREEN, "Turnuvaya katildiniz."));
                        } else {
                            src.sendMessage(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.DARK_RED, "Turnuvaya zaten katildiniz."));
                        }
                    }
                } else {
                    src.sendMessage(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.DARK_RED, "Suan aktif bir turnuva bulunmamaktadir."));
                }*/
                return CommandResult.success();
            })
            .build();

    CommandSpec olustur = CommandSpec.builder()
            .description(Text.of("Turnuva olusturmanizi saglar."))
            .permission("admin.turolustur")
            .executor((CommandSource src, CommandContext args) -> {
                try {
                    connect = DriverManager.getConnection(DB_URL, USER, PASS);
                    try {
                        Class.forName(JDBC_DRIVER);
                        statmt = connect.createStatement();

                        String sqlara = "SELECT id FROM Turnuva ORDER BY id DESC";
                        ResultSet rsara = statmt.executeQuery(sqlara);

                        rsara.next();
                        int idr1 = rsara.getInt("id");
                        int id = idr1 + 1;

                        statmt = connect.createStatement();
                        if (Sponge.getServer().getPlayer(src.getName()).isPresent() && tOn != 1) {
                            String sqlturnuva = "INSERT INTO Turnuva(id, durum, tur, vsdurum, tekrar, kayit, rs1, rs2, len, t1liste, t2liste, t3liste, t4liste, t5liste, t6liste, t7liste, vs, player1, player2) VALUES" + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                            PreparedStatement preparedStatement = connect.prepareStatement(sqlturnuva);
                            preparedStatement.setInt(1, id); // id
                            preparedStatement.setInt(2, 1); // durum = 1
                            preparedStatement.setInt(3, 1); // tur = 1
                            preparedStatement.setInt(4, 0); // vsdurum = 0
                            preparedStatement.setInt(5, 0); // tekrar = 0
                            preparedStatement.setInt(6, 1); // kayit = 1
                            preparedStatement.setInt(7, 0); // rs1 = 0
                            preparedStatement.setInt(8, 0); // rs2 = 0
                            preparedStatement.setInt(9, 4); // len = 0
                            //preparedStatement.setString(10, ""); // t1liste = ""
                            preparedStatement.setString(10, "YogurtluKadayif,KadayifliYogurt,Player1,Player2,");
                            preparedStatement.setString(11, ""); // t2liste = ""
                            preparedStatement.setString(12, ""); // t3liste = ""
                            preparedStatement.setString(13, ""); // t4liste = ""
                            preparedStatement.setString(14, ""); // t5liste = ""
                            preparedStatement.setString(15, ""); // t6liste = ""
                            preparedStatement.setString(16, ""); // t7liste = ""
                            preparedStatement.setString(17, ""); // vs = ""
                            preparedStatement.setString(18, ""); // player1 = ""
                            preparedStatement.setString(19, ""); // player2 = ""
                            preparedStatement.executeUpdate();

                            tOn = 1;
                            ktGuncelle(1);

                            src.sendMessage(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.GREEN, "Turnuvayi basariyla olusturdunuz."));
                            Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.GREEN, "Yeni bir turnuva olusturuldu. /yturnuva"));
                        }
                    } catch (SQLException se) {
                        se.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (statmt != null) statmt.close();
                        } catch (SQLException se2) {
                        }
                        try {
                            if (connect != null) connect.close();
                        } catch (SQLException se) {
                            se.printStackTrace();
                        }
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /*tOn = 1;
                tKayit = 1;
                src.sendMessage(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.GREEN, "Turnuvayi basariyla olusturdunuz."));
                Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.BLUE, "[", TextColors.AQUA, "Turnuva", TextColors.BLUE, "] ", TextColors.GREEN, "Yeni bir turnuva olusturuldu. /yturnuva"));
                tur = 2;*/
                return CommandResult.success();
            })
            .build();

    CommandSpec komut = CommandSpec.builder()
            .description(Text.of("KadayifTurnuva"))
            .permission("kturnuva.kturnuva")
            .child(katil, "katil")
            .child(olustur, "adolustur")
            .child(baslat, "adbaslat")
            .executor((CommandSource src, CommandContext args) -> {
                src.sendMessage(Text.of(" "));
                src.sendMessage(Text.of(TextColors.DARK_BLUE, "-----", TextColors.BLUE, "KadayifTurnuva v1.0", TextColors.DARK_BLUE, "-----"));
                src.sendMessage(Text.builder("/yturnuva katil                   ").color(TextColors.DARK_AQUA).append(
                        Text.builder("| Turnuvaya katilmanizi saglar.").color(TextColors.DARK_PURPLE).build()).build()
                );
                return CommandResult.success();
            }).build();
}
