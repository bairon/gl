package pp;

import pp.model.AModel;
import pp.model.Application;
import pp.model.Armor;
import pp.model.Btl;
import pp.model.Challenge;
import pp.model.City;
import pp.model.Glad;
import pp.model.Guild;
import pp.model.IModel;
import pp.model.Player;
import pp.model.Position;
import pp.model.Stuff;
import pp.model.Trnm;
import pp.model.enums.BtlType;
import pp.model.enums.GladType;
import pp.model.enums.StatusType;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public enum ParserMarker {
	// ========================================== GLAD =======================================
	GL_TYPE("/images/types/", ".gif", new ModelUpdater<Glad>(){
		public boolean update(final Glad model, final String text) {
			model.setType(GladType.byID(Long.parseLong(text)));
            return true;
		}
	}, true),
	GL_ID("href=/gladiators/", ">", new ModelUpdater<Glad>(){
		public boolean update(final Glad model, final String text) {
			model.setId(Long.parseLong(text));
            return true;
		}
	}, true),
	RCR_ID("id=", ">", new ModelUpdater<Glad>(){
		public boolean update(final Glad model, final String text) {
			model.setId(Long.parseLong(text));
            return true;
		}
	}, true),
	GL_NAME(">", "[", new ModelUpdater<Glad>(){
		public boolean update(final Glad model, final String text) {
			model.setName(text.trim());
            return true;
		}
	}, true),
	GL_LVL("[", "]</a>", new ModelUpdater<Glad>() {
		public boolean update(final Glad model, final String text) {
			model.setLvl(Long.parseLong(text));
            return true;
		}
	}, true),
	GL_STATUS("/images/status/", ".gif", new ModelUpdater<Glad>() {
		public boolean update(final Glad model, final String text) {
			model.setStatus(StatusType.byId(Long.parseLong(text)));
            return true;
		}
	}, true),
	GL_HP("<center><b>", "</b>", new ModelUpdater<Glad>(){
		public boolean update(final Glad model, final String text) {
			model.setHealth(Long.parseLong(text));
            return true;
		}
	}, true),
	GL_MAX_HP(" (", ")<", new ModelUpdater<Glad>(){
		public boolean update(final Glad model, final String text) {
			model.setMaxHealth(Long.parseLong(text));
            return true;
		}
	}, true),
	GL_AGE("center>", "</td>", new ModelUpdater<Glad>(){
		public boolean update(final Glad model, final String text) {
			model.setAge(Long.parseLong(text));
            return true;
		}
	}, true),
	GL_TAL("<b>", "</td>", new ModelUpdater<Glad>(){
		public boolean update(final Glad model, final String text) {
			model.setTalent(Long.parseLong(text));
            return true;
		}
	}, true),
	RCR_TAL("center>", "</td>", new ModelUpdater<Glad>(){
		public boolean update(final Glad model, final String text) {
			model.setTalent(Long.parseLong(text));
            return true;
		}
	}, true),
	GL_VIT("<center>", "</td>", new ModelUpdater<Glad>(){
		public boolean update(final Glad model, final String text) {
			model.setVitality(Long.parseLong(text));
            return true;
		}
	}, true),
	GL_DEX("<center>", "</td>", new ModelUpdater<Glad>(){
		public boolean update(final Glad model, final String text) {
			model.setDexterity(Long.parseLong(text));
            return true;
		}
	}, true),
	GL_ACC("<center>", "</td>", new ModelUpdater<Glad>(){
		public boolean update(final Glad model, final String text) {
			model.setAccuracy(Long.parseLong(text));
            return true;
		}
	}, true),
	GL_STR("<center>", "</td>", new ModelUpdater<Glad>(){
		public boolean update(final Glad model, final String text) {
			model.setStrength(Long.parseLong(text));
            return true;
		}
	}, true),
	GL_PRICE("\"right\"><nobr>", "</td>", new ModelUpdater<Glad>(){
		public boolean update(final Glad model, final String text) {
			model.setPrice(Double.parseDouble(text.replace(".", "")));
            return true;
		}
	}, true),
	GL_LVLUP("level.php?id=", ">", new ModelUpdater<Glad>() {
		public boolean update(final Glad model, final String text) {
			model.setLvlUp(true);
            return true;
		}
	}, false),
	GL_EXP("<b>", "</b>", new ModelUpdater<Glad>() {
		public boolean update(final Glad model, final String text) {
			model.setExp(Long.parseLong(text));
            return true;
		}
	}, true),
	GL_EXP_TO_LVL("(", ")", new ModelUpdater<Glad>() {
		public boolean update(final Glad model, final String text) {
			model.setExpToLvl(Long.parseLong(text));
            return true;
		}
	}, true),
	GL_INJURED("травмирован", "<", new ModelUpdater<Glad>() {
		@Override
		public boolean update(Glad model, String text) {
			model.setHealth(0L);
            return true;
		}
	}, false),
	GL_HP_PERCENT("OldStamina\"  value=\"", "\"", new ModelUpdater<Glad>() {
		@Override
		public boolean update(Glad model, String text) {
			model.setHpPercent(Double.parseDouble(text));
            return true;
		}
	}, false),
	GL_PREDMORALE("M\"", " ", null, true),
	GL_MORALE("value=", ">", new ModelUpdater<Glad>() {
		public boolean update(final Glad model, final String text) {
			model.setMorale(Long.parseLong(text));
            return true;
		}
	}, true),
	GL_PREDINJURY("I\"", " ", null, true),
	GL_INJURY("value=", ">", new ModelUpdater<Glad>(){
		public boolean update(final Glad model, final String text) {
			model.setInjury(Double.parseDouble(text));

            return true;
		}
	}, true),
	GL_END("IMinus", "class", null, true),
	// =========================================== STUFF ==============================
	STF_SINGLE("Работоспособность", ":", new ModelUpdater<Stuff>() {
		@Override
		public boolean update(final Stuff model, final String text) {
			model.setId(1L);
            return true;
		}
	}, true),
	STF_MASSEUR_MRK("Массажист", "-", null, false),
	STF_MASSEUR("value=", ">%", new ModelUpdater<Stuff>() {
		@Override
		public boolean update(final Stuff model, final String text) {
			model.setMasseur(Long.parseLong(text));
            return true;
		}
	}, false),
	STF_PRIEST_MRK("Жрец", "-", null, false),
	STF_PRIEST("value=", ">%", new ModelUpdater<Stuff>() {
		@Override
		public boolean update(final Stuff model, final String text) {
			model.setPriest(Long.parseLong(text));
            return true;
		}
	}, false),
	STF_DOCTOR_MRK("Лекарь", "-", null, false),
	STF_DOCTOR("value=", ">%", new ModelUpdater<Stuff>() {
		@Override
		public boolean update(final Stuff model, final String text) {
			model.setDoctor(Long.parseLong(text));
            return true;
		}
	}, false),
	STF_RST_GLD("Восстановить всех гладиаторов [Бесплатно: ", "]", new ModelUpdater<Stuff>() {
		@Override
		public boolean update(final Stuff model, final String text) {
			model.setRstGld(Long.parseLong(text));
            return true;
		}
	}, false),
	STF_RST_SPC("Восстановить специалистов [Бесплатных бонусов: ", "]", new ModelUpdater<Stuff>() {
		@Override
		public boolean update(final Stuff model, final String text) {
			model.setRstSpc(Long.parseLong(text));
            return true;
		}
	}, false),

	// ========================================== TRNM ================================

	TRN_ID("top><a href=/xml/arena/tournaments.php?id=", ">", new ModelUpdater<Trnm>() {
		public boolean update(final Trnm model, final String text) {
			model.setId(Long.parseLong(text));
            return true;
		}
	}, true),
    TRNM_PRE_NAME("<a href=\"tournaments.php?id=", "</a>", new ModelUpdater<Trnm>() {
        @Override
        public boolean update(Trnm model, String text) {
            model.setName(text);
            return true;
        }
    }, true),
    TRNM_POST_NAME("\">", "</a>", new ModelUpdater<Trnm>() {
        @Override
        public boolean update(Trnm model, String text) {
            model.setName(text);
            return true;
        }
    }, true),
    TRNM_NAME("турниры:", "</a>", new ModelUpdater<Trnm>() {
		@Override
		public boolean update(Trnm model, String text) {
			model.setName(text);
            return true;
		}
	}, true),
	TRN_DATE(" //", "</td>", new ModelUpdater<Trnm>() {
		public boolean update(final Trnm model, final String text) {
			try {
				Date date = DATE_FORMAT.parse(text);
				model.setStart(date);
			} catch (ParseException e) {
				e.printStackTrace();
                return false;
			}
            return true;
		}
	}, true),
    TRN_LVL_TO2("гладиаторов ", " уровня", new ModelUpdater<Trnm>() {
        public boolean update(final Trnm model, final String text) {
            if (text.endsWith(".")) {
                model.setLvlTo(Long.parseLong(text.substring(0, text.length()-1)));
                model.setLvlFrom(Long.parseLong(text.substring(0, text.length()-1)));
            } else {
                model.setLvlTo(Long.parseLong(text));
                model.setLvlFrom(Long.parseLong(text));
            }
            return true;
        }
    }, false),
	TRN_LVL_FROM("уровня от ", " до", new ModelUpdater<Trnm>() {
		public boolean update(final Trnm model, final String text) {
			if (text.endsWith(".")) {
				model.setLvlFrom(Long.parseLong(text.substring(0, text.length()-1)));
			} else {
				model.setLvlFrom(Long.parseLong(text));
			}
            return true;
		}
	}, false),
	TRN_LVL_TO(" до ", "<", new ModelUpdater<Trnm>() {
		public boolean update(final Trnm model, final String text) {
            if (text.contains("лвл")){
                model.setLvlTo(Long.parseLong(text.substring(0, text.indexOf("лвл")).trim()));
                return true;
            }
            if (text.endsWith("минут")) return false;
                if (text.endsWith(".")) {
                    model.setLvlTo(Utils.findNumber(text.substring(0, text.length() - 1)));

                } else {
                    model.setLvlTo(Long.parseLong(text));
                }
            return true;
		}
	}, false),

        TRN_PRIZE("Призовой фонд:</b> ", " денариев", new ModelUpdater<Trnm>() {
		@Override
		public boolean update(final Trnm model, final String text) {
			model.setPrize(Long.parseLong(text));
            return true;
		}
	}, false),
	TRN_MEMBERS("Участников:</b> ", " и", new ModelUpdater<Trnm>() {
		@Override
		public boolean update(final Trnm model, final String text) {
			model.setMembers(Long.parseLong(text));
            return true;
		}
	}, false),
	TRN_MEMBERS2("Участники:", " ", new ModelUpdater<Trnm>() {
		@Override
		public boolean update(final Trnm model, final String text) {
			model.setMembers(Long.parseLong(text));
            return true;
		}
	}, true),

           TRN_MEMBERS_MAX("з ", "<", new ModelUpdater<Trnm>() {
		@Override
		public boolean update(final Trnm model, final String text) {
			model.setMaxMembers(Long.parseLong(text.replace(":", "").trim()));
            return true;
		}
	}, false),
    TRN_END_MARKER("подробнее", "..", new ModelUpdater<Trnm>() {
        @Override
        public boolean update(final Trnm model, final String text) {
            return true;
        }
    }, true),

	TRN_CAN_JOIN("[присоединиться", "]", new ModelUpdater<Trnm>() {
		public boolean update(final Trnm model, final String text) {
			model.setCanJoin(true);
            return true;
		}
	}, false),
	TRN_CAN_LEAVE("[отказаться от участия", "]", new ModelUpdater<Trnm>() {
		public boolean update(final Trnm model, final String text) {
			model.setCanLeave(true);
            return true;
		}
	}, false),
	TRN_CAN_USE_TICKET("[использовать приглашение", "]", new ModelUpdater<Trnm>() {
		public boolean update(final Trnm model, final String text) {
			model.setCanUseTicket(true);
            return true;
		}
	}, false),
	TRN_SENATE("Сенаторские турниры:", " ", null, true),
	TRN_COMPLETE("завершен", ".", new ModelUpdater<Trnm>() {
		@Override
		public boolean update(final Trnm model, final String text) {
			model.setCompleted(true);
            return true;
		}
	}, false),
	TRN_WINNER("/users/", "/>", new ModelUpdater<Trnm>() {
		@Override
		public boolean update(final Trnm model, final String text) {
			model.setWinner(Long.parseLong(text));
            return true;
		}
	}, false),
	TRN_WINNER_NAME("/>", "<", new ModelUpdater<Trnm>() {
		@Override
		public boolean update(final Trnm model, final String text) {
			model.setWinnerName(text);
            return true;
		}
	}, false),
	TRN_FINAL("/users/", "/>", new ModelUpdater<Player>() {
		@Override
		public boolean update(final Player model, final String text) {
			model.setId(Long.parseLong(text));
            return true;
		}
	}, true),
	TRN_MEMBER("/users/", ">", new ModelUpdater<Player>() {
		@Override
		public boolean update(final Player model, final String text) {
			model.setId(Long.parseLong(text));
            return true;
		}
	}, true),
	TRN_MEMBER_GUILD("/guilds/", "/>", new ModelUpdater<Player>() {
		@Override
		public boolean update(final Player model, final String text) {
			model.setGuildId(Long.parseLong(text));
            return true;
		}
	}, false),
	TRN_MEMBER_NAME(">", "<", new ModelUpdater<Player>() {
		@Override
		public boolean update(final Player model, final String text) {
			model.setName(text);
            return true;
		}
	}, false),


	// ============================================= BTTL ===================================

	BTL_START("-- task bar --", ">", null, true),

	BTL_OPP_GUILD("/images/gd_guilds/small/", ".jpg", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setOppGuild(Long.parseLong(text));
            return true;
		}
	}, false),
	BTL_FIGHT_ID("/xml/arena/fight.php?id=", "\"", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setId(Long.parseLong(text));
            return true;
		}
	}, false),
	BTL_CHECK("[проверить", "]", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setCheck(true);
            return true;
		}
	}, false),
	BTL_FIGHT_DONE_ID("/fights/", "\"", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setId(Long.parseLong(text));
            return true;
		}
	}, false),
	BTL_FIGHT("перейти к просмотру", "<", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setFight(true);

            return true;
		}
	}, false),
	BTL_BUILDER_ID("/builder/?id=", "\"", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			if (text.length() <= 10)
			model.setId(Long.parseLong(text));
            return true;
		}
	}, false),

	BTL_BUILDER_ID2("/builder/?id=", "<", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			if (text.length() <= 10)
			model.setId(Long.parseLong(text));
            return true;
		}
	}, false),
	BTL_PREPARE("[настройки боя", "]", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setPrepare(true);
            return true;
		}
	}, false),
	BTL_PREPARE2("[настройки на бой", "]", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setPrepare(true);
            return true;
		}
	}, false),
	BTL_BACK("[вернуться к настройкам", "]", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setBack(true);

            return true;
		}
	}, false),
	BTL_TIMEOUT("<span id=\"Timer\">", "</span>", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setTimeOut(text.contains("-"));

            return true;
		}
	}, true),
	BTL_END("-- /task bar --", ">", null, true),


	// ================================================ BLD =================================

	BLD_FLASHVARS("var flashvars = ", "{};", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setVars(text);
            return true;
		}
	}, true),
	BLD_TYPE_ID("flashvars.TypeID = \"", "\";", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setType(BtlType.byID(Long.parseLong(text)));
            return true;
		}
	}, false),
	BLD_TYPE_ID2("flashvars.TypeID = encodeURIComponent(\"", "\");", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setType(BtlType.byID(Long.parseLong(text)));
            return true;
		}
	}, false),
	BLD_LIMIT_GLAD("flashvars.LimitGlad = \"", "\";", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setLimitGlad(Long.parseLong(text));
            return true;
		}
	}, false),
	BLD_LIMIT_GLAD2("flashvars.LimitGlad = encodeURIComponent(\"", "\");", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setLimitGlad(Long.parseLong(text));
            return true;
		}
	}, false),
	BLD_LIMIT_SKL("flashvars.LimitSkl = \"", "\";", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setLimitSkl(Long.parseLong(text));
            return true;
		}
	}, false),
	BLD_LIMIT_SKL2("flashvars.LimitSkl = encodeURIComponent(\"", "\");", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setLimitSkl(Long.parseLong(text));
            return true;
		}
	}, false),
	BLD_MAX_LVL2("flashvars.MaxLevel = encodeURIComponent(\"", "\");", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setMaxLevel(Long.parseLong(text));
            return true;
		}
	}, false),
	BLD_MAX_LVL("flashvars.MaxLevel = \"", "\";", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setMaxLevel(Long.parseLong(text));
            return true;
		}
	}, false),
	BLD_XML("flashvars.xml = \"", "\";", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setXml(text);
            return true;
		}
	}, false),
	BLD_XML2("flashvars.xml = encodeURIComponent(\"", "\");", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setXml(text);
            return true;
		}
	}, false),
	BLD_TIME_LEFT("flashvars.time_left = \"", "\";", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setTimeLeft(Long.parseLong(text));
            return true;
		}
	}, false),
	BLD_TIME_LEFT2("flashvars.time_left = encodeURIComponent(\"", "\");", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setTimeLeft(Long.parseLong(text));
            return true;
		}
	}, false),
	BLD_GLAD_TIP("flashvars.glad_tip = \"", "\";", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setGladTip(text);
            return true;
		}
	}, false),
	BLD_GLAD_TIP2("flashvars.glad_tip = encodeURIComponent(\"", "\");", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setGladTip(text);
            return true;
		}
	}, false),
	BLD_ID("flashvars.id = \"", "\";", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setFvid(Long.parseLong(text));
			model.setId(Long.parseLong(text));
            return true;
		}
	}, false),
	BLD_ID2("flashvars.id = encodeURIComponent(\"", "\");", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setFvid(Long.parseLong(text));
			model.setId(Long.parseLong(text));
            return true;
		}
	}, false),
	BLD_USER1_LOGIN("flashvars.user1Login = \"", "\";", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setUser1Login(text);
            return true;
		}
	}, false),
	BLD_USER1_LOGIN2("flashvars.user1Login = encodeURIComponent(\"", "\");", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setUser1Login(text);
            return true;
		}
	}, false),
	BLD_USER2_LOGIN("flashvars.user2Login = \"", "\";", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setUser2Login(text);
            return true;
		}
	}, false),
	BLD_USER2_LOGIN2("flashvars.user2Login = encodeURIComponent(\"", "\");", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setUser2Login(text);
            return true;
		}
	}, false),
	BLD_TOURMENT_TITLE("flashvars.tourmentTitle = \"", "\";", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setTourmentTitle(text);
            return true;
		}
	}, false),
	BLD_TOURMENT_TITLE2("flashvars.tourmentTitle = encodeURIComponent(\"", "\");", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setTourmentTitle(text);
            return true;
		}
	}, false),
	BLD_CHAMP("flashvars.champ = \"", "\";", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setChamp(Long.parseLong(text));
            return true;
		}
	}, false),
	BLD_CHAMP2("flashvars.champ = encodeURIComponent(\"", "\");", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setChamp(Long.parseLong(text));
            return true;
		}
	}, false),
	BLD_MIN_AWARD_FEE("flashvars.minAwardFee = \"", "\";", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setMinAwardFee(Long.parseLong(text));
            return true;
		}
	}, false),
	BLD_MIN_AWARD_FEE2("flashvars.minAwardFee = encodeURIComponent(\"", "\");", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setMinAwardFee(Long.parseLong(text));
            return true;
		}
	}, false),
	BLD_TUT("flashvars.tut = \"", "\";", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setTut(text);
            return true;
		}
	}, false),
	BLD_TUT2("flashvars.tut = encodeURIComponent(\"", "\");", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setTut(text);
            return true;
		}
	}, false),
	BLD_USER1_URL("flashvars.user1Url = \"", "\";", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setUser1Url(text);
            return true;
		}
	}, false),
	BLD_USER1_URL2("flashvars.user1Url = encodeURIComponent(\"", "\");", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setUser1Url(text);
            return true;
		}
	}, false),
	BLD_USER2_URL("flashvars.user2Url = \"", "\";", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setUser2Url(text);
            return true;
		}
	}, false),
	BLD_USER2_URL2("flashvars.user2Url = encodeURIComponent(\"", "\");", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setUser2Url(text);
            return true;
		}
	}, false),
	BLD_ARMOR_LEVELS("flashvars.armorLevels = \"", "\";", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setArmorLevels(text);
            return true;
		}
	}, false),
	BLD_ARMOR_LEVELS2("flashvars.armorLevels = \"", "\");", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setArmorLevels(text);
            return true;
		}
	}, false),
	BLD_URL("flashvars.url = \"", "\";", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setUrl(text);
            return true;
		}
	}, false),
	BLD_URL2("flashvars.url = encodeURIComponent(\"", "\");", new ModelUpdater<Btl>() {
		@Override
		public boolean update(final Btl model, final String text) {
			model.setUrl(text);
            return true;
		}
	}, false),
    BLD_ORDER("flashvars.order", "xml = ", new ModelUpdater() {
        @Override
        public boolean update(IModel model, String text) {

            return true;
        }
    }, true),
	GLDS_ID("/guilds/", ">", new ModelUpdater<Guild>(){
		@Override
		public boolean update(final Guild model, final String text) {
			model.setId(Long.parseLong(text));
            return true;
		}
	}, true),
	GLDS_NAME(">", "<", new ModelUpdater<Guild>(){
		@Override
		public boolean update(final Guild model, final String text) {
			model.setName(text);
            return true;
		}
	}, true),
	PL_MARKER("UserID[", "]", null, true),
	PL_ID("/users/", ">", new ModelUpdater<Player>() {
		@Override
		public boolean update(final Player model, final String text) {
            model.setId(Utils.findNumber(text));
            return true;
		}
	} , true),
	PL_NAME(">", "<", new ModelUpdater<Player>() {
		@Override
		public boolean update(final Player model, final String text) {
			model.setName(text);
            return true;
		}
	}, true),
	PL_LVL("> [", "]", new ModelUpdater<Player>() {
		@Override
		public boolean update(final Player model, final String text) {
			model.setLvl(Long.parseLong(text));
            return true;
		}
	}, true),
	PL_VIP("/images/vip/", ".gif", new ModelUpdater<Player>() {
		@Override
		public boolean update(final Player model, final String text) {
			model.setVip(Long.parseLong(text));
            return true;
		}
	}, false),
	PL_ONLINE("online.gif", " ", new ModelUpdater<Player>() {
		@Override
		public boolean update(final Player model, final String text) {
			model.setOnline(true);
            return true;
		}
	}, false),
	PL_PTS("<center>", "</td>", new ModelUpdater<Player>() {
		@Override
		public boolean update(final Player model, final String text) {
			model.setPts(text);
            return true;
		}
	}, true),
	MAP_GUILD_ID("/images/gd_guilds/small/", ".jpg", new ModelUpdater<Guild>() {
		@Override
		public boolean update(final Guild model, final String text) {
			model.setId(Long.parseLong(text));
            return true;
		}
	}, true),
	MAP_GUILD_PTS("<center>", "</td>", new ModelUpdater<Guild>() {
		@Override
		public boolean update(final Guild model, final String text) {
			model.setPoints(Long.parseLong(text));
            return true;
		}
	},true),


	DUE_ID("act=delete&id=", "&step=1&type=finances/dues_history", new ModelUpdater<AModel>() {
		@Override
		public boolean update(AModel model, String text) {
			model.setId(Long.parseLong(text));
            return true;
		}
	}, true),
    APP_ID("]\"  value=\"", "\"/>", new ModelUpdater<Application>() {
        @Override
        public boolean update(Application model, String text) {
            model.setId(Long.parseLong(text));
            return true;
        }
    }, true),
    APP_USER_ID("/users/", "</a>", new ModelUpdater<Application>() {
        @Override
        public boolean update(Application model, String text) {
            String[] split = text.split(">");
            model.userId = Long.parseLong(split[0]);
            model.userName = split[1];
            return true;
        }
    }, true),
    APP_TIME("<center>", "</td>", new ModelUpdater<Application>() {
        @Override
        public boolean update(Application model, String text) {
            model.time = text;
            return true;
        }
    }, true),
    APP_TYPES("<center>", "</td>", new ModelUpdater<Application>() {
        @Override
        public boolean update(Application model, String text) {
            model.alltypes = text.contains("все основные типы");
            return true;
        }
    }, true),
    APP_GLAD_LIMIT("<center>", "</td>", new ModelUpdater<Application>() {
        @Override
        public boolean update(Application model, String text) {
            model.gladlimit = Integer.parseInt(text);
            return true;
        }
    }, true),
    APP_GLAD_LEVEL("<center>", "</td>", new ModelUpdater<Application>() {
        @Override
        public boolean update(Application model, String text) {
            model.gladlevel = Integer.parseInt(text);
            return true;
        }
    }, true),
    APP_TOTAL_LEVEL("<center>", "</td>", new ModelUpdater<Application>() {
        @Override
        public boolean update(Application model, String text) {
            model.totallevel = Integer.parseInt(text);
            return true;
        }
    }, true),

    ATTACKING(">Атакует: <img src=/images/gd_guilds/small/", ".jpg", new ModelUpdater<Challenge>() {
        @Override
        public boolean update(Challenge model, String text) {
            model.attackerGId = Long.parseLong(text);
            return true;
        }
    }, true),
    DEFENDING("Защищается: <img src=/images/gd_guilds/small/",".jpg", new ModelUpdater<Challenge>() {
        @Override
        public boolean update(Challenge model, String text) {
            model.defenderGId = Long.parseLong(text);
            return true;
        }
    }, true),
    CHALLENGE_STARTED("Турнир начался", "", new ModelUpdater<Challenge>() {
        @Override
        public boolean update(Challenge model, String text) {
            model.started = true;
            return true;
        }
    }, false),
    CHALLENGE_ID("challenges.php?id=", ">подробнее...", new ModelUpdater<Challenge>() {
        @Override
        public boolean update(Challenge model, String text) {
            model.setId(Long.parseLong(text));
            return true;
        }
    }, true),
    TR_START("<tr", " ", null, true),
    POSITION_DEFENDING_USER("/users/", "</a>", new ModelUpdater<Position>() {
        @Override
        public boolean update(Position model, String text) {
            model.defender = text.split(">")[1];
            return true;
        }
    }, false),
    POSITION_ID("&pid=", ">", new ModelUpdater<Position>() {
        @Override
        public boolean update(Position model, String text) {
            model.setId(Long.parseLong(text));
            return true;
        }
    }, false),
    POSITION_TAKE("занять", "<", new ModelUpdater<Position>() {
        @Override
        public boolean update(Position model, String text) {
            model.attacker = null;
            model.defender = null;
            return true;
        }
    }, false),
    POSITION_ATTACK("атаковать", "<", new ModelUpdater<Position>() {
        @Override
        public boolean update(Position model, String text) {
            model.attacker = null;
            return true;
        }
    }, false),
    CAN_TAKE_GOLD_ARMOR("Вы можете забрать зол", "той комплект", null, true),
    CAN_TAKE_SILVER_ARMOR("Вы можете забрать сер", "ный комплект", null, true),
    ARMOR_ID("ArmorID", "/>", new ModelUpdater<AModel>() {
        @Override
        public boolean update(AModel model, String text) {
            String idtext = Utils.between(text, "value=\"", "\"");
            model.setId(Long.parseLong(idtext));
            return true;
        }
    }, true),
    COMPLECT("омплект", "</b>", null, true),
    COMPLECT_ID("document.location='?id=", "&act=buy'", new ModelUpdater<Armor>() {
        @Override
        public boolean update(Armor model, String text) {
            model.setId(Long.parseLong(text));
            return true;
        }
    }, true),
    COMPLECT_MORALE("Мораль: <b>+", "</b><br>Прочность", new ModelUpdater<Armor>() {
        @Override
        public boolean update(Armor model, String text) {
            model.setMorale(Long.parseLong(text));
            return true;
        }
    }, true),
    TAKE_FROM_HOSTEL("act=takefromhotel&gladiator=", "&step=1>[забрать в отряд]", new ModelUpdater<AModel>() {
        @Override
        public boolean update(AModel model, String text) {
            model.setId(Long.parseLong(text));
            return true;
        }
    }, true)

    ;

	//<i>Турнир с возрастанием уровня от 10 до 15</i>

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy hh:mm");
	private String start;
	private String end;
	private ModelUpdater updater;
	private boolean required;

	ParserMarker(final String start, final String end, final ModelUpdater updater, final boolean required) {
		this.start = start;
		this.end = end;
		this.updater = updater;
		this.required = required;
	}

	public String getStart() {
		return start;
	}

	public String getEnd() {
		return end;
	}

	public boolean isRequired() {
		return required;
	}

	public ModelUpdater<IModel> getUpdater() {
		return updater;

	}
}
