package pp;

import pp.model.IModel;
import pp.model.Tag;
import pp.model.enums.TagType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class GlRuParser {
	public static final String SCRIPT_TAG_NAME = "script";

	public <T extends IModel> void parsePage(final String page, final ParserMarker[] markers, final ModelFactory modelFactory, final MergingStrategy mergingStrategy, Map<Long,
			? extends IModel> result,
	                      boolean removeScripts) {
		try {
			if (mergingStrategy == MergingStrategy.REPLACE) {
				result.clear();
			}
			String html = removeScripts ? removeTag(page, SCRIPT_TAG_NAME) : page;
			IModel source = modelFactory.getNewModel();
			//find required marker index
			if (markers.length > 0) {
				int requiredFirstMarkerIndex = -1;
				int requiredLastMarkerIndex = markers.length;
				while(!markers[++requiredFirstMarkerIndex].isRequired());
				while(!markers[--requiredLastMarkerIndex].isRequired());
				ParserMarker first = markers[0];
				int start = 0;
				List<Integer> firsts = new ArrayList<Integer>();
				List<Integer> lasts = new ArrayList<Integer>();
				lasts.add(0);
				while((start = html.indexOf(markers[requiredFirstMarkerIndex].getStart(), firsts.size() == 0 ? 0 : firsts.get(firsts.size()-1) + 1)) >= 0) {
					firsts.add(start);
				}
				firsts.add(html.length());
				start = 0;
				while((start = html.indexOf(markers[requiredLastMarkerIndex].getStart(), lasts.size() == 0 ? 0 : firsts.get(lasts.size()-1))) >= 0) {
					lasts.add(start);
				}

/*
				for (int i = 0; i < firsts.size() - 1; ++i) {
					System.out.print(html.substring(lasts.get(i), firsts.get(i)));
					System.out.print("***");
					System.out.println(html.substring(firsts.get(i), lasts.get(i+1)));
					System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
				}
				System.out.println(html.substring(lasts.get(lasts.size()-1), firsts.get(firsts.size()-1)));
*/

				int pos = 0;
				for (int k = 0; k < firsts.size() - 1; ++k) {
					int f = firsts.get(k);
					int l = lasts.get(k);
					int nf = firsts.get(k + 1);
					int nl = lasts.get(k + 1);
					for (int i = 0; i < markers.length; ++i) {
						ParserMarker marker = markers[i];
						String debug = "";
						try {
							start = html.indexOf(marker.getStart(), pos);
							int end = html.indexOf(marker.getEnd(), start + marker.getStart().length());
							int debugStart = start - 300 < 0 ? 0 : start - 300;
							int debugEnd = end + 300 >= html.length()-1 ? html.length()-1 : end + 300;
							if (start != -1 && end != -1) {
								debug = html.substring(debugStart, start) + "***" + html.substring(start, debugEnd);
							}
							if (i <  requiredFirstMarkerIndex && l <= start && start < end && end < f) {
								String text = html.substring(start + marker.getStart().length(), end);
								ModelUpdater<IModel> updater = marker.getUpdater();
                                boolean updateResult = true;
								if (updater != null) {
										updateResult = updater.update(source, text);
								}
                                if (updateResult) {
                                    pos = end;
                                }
							}
							if (i == requiredFirstMarkerIndex && start < end && end <= nl) {
								String text = html.substring(start + marker.getStart().length(), end);
								ModelUpdater<IModel> updater = marker.getUpdater();
                                boolean updateResult = true;
								if (updater != null) {
                                    updateResult = updater.update(source, text);
								}
                                if (updateResult) {
                                    pos = end;
                                }
							}
							if (requiredFirstMarkerIndex < i && i < requiredLastMarkerIndex && f < start && start < end && end < nl) {
								String text = html.substring(start + marker.getStart().length(), end);
								ModelUpdater<IModel> updater = marker.getUpdater();
                                boolean updateResult = true;
                                if (updater != null) {
                                    updateResult = updater.update(source, text);
                                }
                                if (updateResult) {
                                    pos = end;
                                }
							}
							if (i == requiredLastMarkerIndex && start < end && end <= nf) {
								String text = html.substring(start + marker.getStart().length(), end);
								ModelUpdater<IModel> updater = marker.getUpdater();
                                boolean updateResult = true;
								if (updater != null) {
									if (text.length() > 60) {
										System.out.println(text);
									}
                                    updateResult = updater.update(source, text);
								}
                                if (updateResult) {
                                    pos = end;
                                }
							}
							if (requiredLastMarkerIndex < i && nl < start && start < end && end < nf) {
								String text = html.substring(start + marker.getStart().length(), end);
								ModelUpdater<IModel> updater = marker.getUpdater();
                                boolean updateResult = true;
                                if (updater != null) {
                                    updateResult = updater.update(source, text);
                                }
                                if (updateResult) {
                                    pos = end;
                                }
							}
						} catch (Exception e) {
							System.out.println(debug);
							e.printStackTrace();
						}
					}
					if (source.getId() == null) continue;
					if (mergingStrategy == MergingStrategy.MERGE) {
						IModel destination = result.get(source.getId());
						if (destination == null) {
							destination = source;
						} else {
							modelFactory.merge(source, destination);
						}
						((Map<Long, IModel>)result).put(destination.getId(), destination);
					} else if (mergingStrategy == MergingStrategy.REPLACE) {
						((Map<Long, IModel>)result).put(source.getId(), source);
					} else if (mergingStrategy == MergingStrategy.ADD) {
						if (result.get(source.getId()) == null) {
							((Map<Long, IModel>)result).put(source.getId(), source);
						}
					}
					source = modelFactory.getNewModel();
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void parseMyGlads(final String page, final Map<Long, IModel> model) {
		ParserMarker[] markers = new ParserMarker[]{ParserMarker.GL_TYPE, ParserMarker.GL_ID, ParserMarker.GL_NAME, ParserMarker.GL_LVL, ParserMarker.GL_STATUS,
				ParserMarker.GL_HP, ParserMarker.GL_MAX_HP, ParserMarker.GL_AGE, ParserMarker.GL_TAL, ParserMarker.GL_VIT, ParserMarker.GL_DEX, ParserMarker.GL_ACC,
				ParserMarker.GL_STR, ParserMarker.GL_PRICE};
		parsePage(page, markers, new GladModelFactory(), MergingStrategy.MERGE, model, true);
	}

	public void parseTrain(final String page, final Map<Long, IModel> model) {
		ParserMarker[] markers = new ParserMarker[]{ParserMarker.GL_TYPE, ParserMarker.GL_ID, ParserMarker.GL_NAME, ParserMarker.GL_LVL, ParserMarker.GL_HP,
				ParserMarker.GL_MAX_HP, ParserMarker.GL_AGE, ParserMarker.GL_TAL, ParserMarker.GL_LVLUP, ParserMarker.GL_EXP, ParserMarker.GL_EXP_TO_LVL};
		parsePage(page, markers, new GladModelFactory(), MergingStrategy.MERGE, model, true);
	}

	public void parseRecovery(final String page, final Map<Long, IModel> model) {
		ParserMarker[] markers = new ParserMarker[]{ParserMarker.GL_TYPE, ParserMarker.GL_ID, ParserMarker.GL_PREDMORALE, ParserMarker.GL_MORALE, ParserMarker.GL_PREDINJURY,
				ParserMarker.GL_INJURY};
		parsePage(page, markers, new GladModelFactory(), MergingStrategy.MERGE, model, true);

	}

	public void parseTrnmnts(final String page, final Map<Long, IModel> model) {
		ParserMarker[] markers = new ParserMarker[]{ParserMarker.TRN_ID, ParserMarker.TRN_DATE, ParserMarker.TRN_LVL_FROM, ParserMarker.TRN_LVL_TO, ParserMarker.TRN_PRIZE,
				ParserMarker.TRN_MEMBERS,  ParserMarker.TRN_MEMBERS_MAX, ParserMarker.TRN_CAN_JOIN, ParserMarker.TRN_CAN_LEAVE};
		parsePage(page, markers, new TrnmModelFactory(), MergingStrategy.REPLACE, model, true);
	}

	private static String removeTag(final String html, final String tagname) {
		StringBuilder sb = new StringBuilder(html.length());
		String opening = "<" + tagname;
		String closing = "</" + tagname + ">";
		int closeTagLength = closing.length();
		int from = 0;
		int start;
		int end;
		do {
			start = html.indexOf(opening, from);
			end = html.indexOf(closing, from + 1);
			if (start > from && end > start) {
				sb.append(html.substring(from, start));
				from = end + closeTagLength;
			}
		} while (start != -1);
		if (from < html.length()) {
			sb.append(html.substring(from));
		}
		return sb.toString();
	}
	private static String removeTag(final String page, final String tagname, final int startIndex, final List<String> includes, final List<String> excludes) {

		return null;
	}
	private static Tag findTag(final String page, final String tagname, final int startIndex) {
		boolean insideStr = false;
		int op = page.indexOf("<" + tagname, startIndex);
		int cl = page.indexOf("</" + tagname, startIndex);
		op = op == -1 ? Integer.MAX_VALUE : op;
		cl = cl == -1 ? Integer.MAX_VALUE : cl;
		int em = page.indexOf("/>", op);
		int c = page.indexOf(">", Math.min(op, cl));
		em = em == -1 ? Integer.MAX_VALUE : em;
		c = c == -1 ? Integer.MAX_VALUE : c;
		if (op <= c && c < Math.min(cl, em))
			return new Tag(op, c + 1, TagType.OPENING, page);
		if (cl <= c && c < Math.min(op, em))
			return new Tag(cl, c + 1, TagType.CLOSING, page);
		if (op <= em && em < Math.min(cl, c))
			return new Tag(op, em + 2, TagType.EMPTY, page);
		return null;
	}
	//private static Tag tag(final String page, final int startIndex) {
	//
	//}


	public void parseBuilder(final String page, final Map<Long, IModel> bttls) {
		ParserMarker [] markers = new ParserMarker[]{
				ParserMarker.BLD_FLASHVARS,
				ParserMarker.BLD_TYPE_ID,
				ParserMarker.BLD_LIMIT_GLAD,
				ParserMarker.BLD_LIMIT_SKL,
				ParserMarker.BLD_MAX_LVL,
				ParserMarker.BLD_XML,
				ParserMarker.BLD_TIME_LEFT,
				ParserMarker.BLD_GLAD_TIP,
				ParserMarker.BLD_ID,
				ParserMarker.BLD_USER1_LOGIN,
				ParserMarker.BLD_USER2_LOGIN,
				ParserMarker.BLD_TOURMENT_TITLE,
				ParserMarker.BLD_CHAMP,
				ParserMarker.BLD_MIN_AWARD_FEE,
				ParserMarker.BLD_TUT,
				ParserMarker.BLD_USER1_URL,
				ParserMarker.BLD_USER2_URL,
				ParserMarker.BLD_ARMOR_LEVELS,
				ParserMarker.BLD_URL,
		};
		parsePage(page, markers, new BtlModelFactory(), MergingStrategy.MERGE, bttls, false);
	}

	public String parseFight(final String page) {
		String startMarker = "Победил";
		String endMarker = "!";
		int start = page.indexOf(startMarker);
		int end = page.indexOf(endMarker, start + startMarker.length());
		if (end > start && start > 0) {
			String winner = page.substring(start + startMarker.length(), end);
			return winner.substring(3);
		}
		return "";
	}

	public void parseStuff(final String page, final Map<Long, IModel> stuff) {
		ParserMarker [] markers = new ParserMarker[]{
				ParserMarker.STF_SINGLE,
				ParserMarker.STF_MASSEUR_MRK,
				ParserMarker.STF_MASSEUR,
				ParserMarker.STF_PRIEST_MRK,
				ParserMarker.STF_PRIEST,
				ParserMarker.STF_DOCTOR_MRK,
				ParserMarker.STF_DOCTOR,
				ParserMarker.STF_RST_GLD,
				ParserMarker.STF_RST_SPC
		};
		parsePage(page, markers, new StuffModelFactory(), MergingStrategy.REPLACE, stuff, true);

	}
	public long parseJoinedTrnm(final String page) {
		String startMarker = "document.location.href='/xml/arena/tournaments.php?id=";
		String endMarker = "'";
		int start = page.indexOf(startMarker);
		int end = page.indexOf(endMarker, start + startMarker.length());
		if (end > start && start > 0) {
			String id = page.substring(start + startMarker.length(), end);
			return Long.parseLong(id);
		}
		return 0;
	}

	public static void main(String[] args) {
		String page = "</div>\n" +
				"</div>\n" +
				"<table id=\"tableMain\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" height=\"100%\" style=\"height: 3px;\">\n" +
				"<tbody/>\n" +
				"<tr>\n" +
				"<td valign=\"top\">\n" +
				"<div class=\"left-panel\" style=\"display: block;\">\n" +
				"<div class=\"script3 lfloat\"></td></table>";
		System.out.println(findTag(page, "table", 0));
		System.out.println(findTag(page, "table", 16));
		System.out.println(findTag(page, "div", 0));
		System.out.println(findTag(page, "tbody", 0));


	}
}