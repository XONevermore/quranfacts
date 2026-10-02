# -*- coding: utf-8 -*-
"""
Facts added in the third batch (October 2026). Same inclusion rule as facts_more.py: a fact is here only if
  (1) it is written in a verse of the Quran, and
  (2) people have actually discovered or measured the thing it points to.
Every number below was checked against the cited paper, press release or reference work when it was written.
Same editorial rules as facts_source.py apply.
"""

THIRD_FACTS = [
    # ============================ SIGNS & HISTORY ============================
    dict(
        id="oldest-manuscripts",
        title="Written Down 1,400 Years Ago",
        hook="“We will be its guardian.” Pages carbon-dated to 568–645 CE carry the same verses, in the same order, that are recited today.",
        category="signs",
        claimType="historical",
        scienceHeading="What the manuscripts show",
        verses=["15:9"],
        words=[
            ("ٱلذِّكْرَ", "adh-dhikr", "the Reminder (the Quran)", "ذ ك ر"),
            ("لَحَٰفِظُونَ", "la-ḥāfiẓūn", "surely its guardians", "ح ف ظ"),
        ],
        science=[
            "In 2015 the University of Birmingham had two parchment leaves from its Mingana Collection radiocarbon-dated at the University of Oxford. The result was 568–645 CE with 95.4% probability: the animal whose skin became the page lived in the lifetime of the Prophet ﷺ (c. 570–632) or within a few years of it.",
            "The leaves hold parts of Surahs 18, 19 and 20 in the early Hijazi script. Their verses are in the order recited today and conform to the standard text. They come from the same manuscript as 16 leaves kept in the Bibliothèque nationale de France in Paris.",
            "They are not alone. A fragment at the University of Tübingen (Ma VI 165) was dated to 649–675 CE in 2014. The Sanaa palimpsest, part of a cache found in 1972 in the Great Mosque of Sanaa, Yemen, was dated to 578–669 CE.",
        ],
        stats=[
            ("568–645 CE", "Birmingham leaves, radiocarbon date (95.4%)"),
            ("649–675 CE", "Tübingen fragment, radiocarbon date"),
            ("1972", "Sanaa manuscripts found in a mosque attic"),
        ],
        discovery=("1972 – 2015", "Sanaa cache · Tübingen · Birmingham & Oxford"),
        media=[
            ("File:Birmingham Quran manuscript.jpg", "The Birmingham leaves: the end of Surah 19 and the start of Surah 20 (left), and Surah 18 (right)."),
            ("File:Sana'a1 Stanford '07 recto.jpg", "A leaf of the Sanaa palimpsest. An older, erased text lies beneath the visible one."),
            ("File:Codex Parisino-petropolitanus, first leaf recto.jpg", "The first page of the Codex Parisino-petropolitanus in Paris, which the Birmingham leaves belong with."),
        ],
        sources=[
            ("University of Birmingham: manuscript dated among the oldest in the world (2015)", "https://www.birmingham.ac.uk/news/2015/birmingham-quran-manuscript-dated-among-the-oldest-in-the-world"),
            ("Birmingham Quran manuscript", "https://en.wikipedia.org/wiki/Birmingham_Quran_manuscript"),
            ("Sadeghi & Bergmann, “The Codex of a Companion of the Prophet”, Arabica 57 (2010)", "https://doi.org/10.1163/157005810X504518"),
            ("Early Quranic manuscripts", "https://en.wikipedia.org/wiki/Early_Quranic_manuscripts"),
        ],
    ),
    dict(
        id="saba-dam",
        title="The Flood of the Dam",
        hook="Saba' had “two gardens, on the right and on the left”, until “the flood of the dam”. The dam, and the two oases on either side of it, were real.",
        category="signs",
        claimType="historical",
        scienceHeading="What archaeology found",
        verses=["34:15", "34:16"],
        words=[
            ("جَنَّتَانِ", "jannatāni", "two gardens", "ج ن ن"),
            ("يَمِينٍۢ", "yamīnin", "the right", "ي م ن"),
            ("سَيْلَ", "sayla", "the flood, the torrent", "س ي ل"),
            ("ٱلْعَرِمِ", "al-ʿarimi", "the dam", "ع ر م"),
        ],
        science=[
            "The Sabaeans of Yemen built a great earth dam upstream of their capital, Maʾrib, to catch the monsoon floods. Its earliest inscription names the ruler Yathaʿ ʾAmar Watar I, who reigned about 760–740 BCE. Around 500 BCE the dam was raised and irrigation was extended from the northern side of the valley to the southern side as well: two oases, one on each side.",
            "At its height the dam stood 14 metres high, with sluices at both ends, and watered about 100 km² of fields. Inscriptions record breaches in 449, 450, 542 and 548 CE and costly repairs, the last in 557. In 570 or 575 the water overtopped it again, and this time it was never repaired. The fields died and many people left Yemen.",
        ],
        stats=[
            ("760–740 BCE", "Reign named in the earliest dam inscription"),
            ("≈100 km²", "Fields the dam watered"),
            ("570 or 575 CE", "Final breach, never repaired"),
        ],
        media=[
            ("File:Marib dam.jpg", "Remains of the ancient dam of Maʾrib, Yemen."),
            ("File:Ancient Ma'rib 01.jpg", "Ruins of ancient Maʾrib, the capital of Saba'."),
        ],
        sources=[
            ("Marib Dam", "https://en.wikipedia.org/wiki/Marib_Dam"),
            ("Sheba (Saba')", "https://en.wikipedia.org/wiki/Sheba"),
            ("Landmarks of the Ancient Kingdom of Saba, Marib", "https://en.wikipedia.org/wiki/Landmarks_of_the_Ancient_Kingdom_of_Saba,_Marib"),
        ],
    ),
    dict(
        id="thamud",
        title="Thamud, Named in Stone",
        hook="The Quran speaks of Thamud, a people of north-west Arabia who carved homes from rock. Assyrian, Greek and Roman-era records name them too.",
        category="signs",
        claimType="historical",
        scienceHeading="What the records show",
        verses=["89:9", "15:80", "15:82"],
        words=[
            ("ثَمُودَ", "thamūda", "Thamud", "ث م د"),
            ("جَابُوا۟", "jābū", "they cut through", "ج و ب"),
            ("ٱلصَّخْرَ", "aṣ-ṣakhra", "the rock", "ص خ ر"),
            ("يَنْحِتُونَ", "yanḥitūna", "they carve", "ن ح ت"),
        ],
        science=[
            "The annals of the Assyrian king Sargon II (reigned 722–705 BCE), inscribed at his palace at Dur-Sharrukin, list Thamud among the Arabian tribes he says he defeated. Greek and Roman writers knew them too: Diodorus in the 1st century BCE, Pliny in the 1st century CE and Ptolemy in the 2nd.",
            "At Ruwāfa in north-west Arabia, about 200 km north-west of Hegra, a temple carries inscriptions in Greek and Nabataean from 165–169 CE. They say “the Thamud” built it, in honour of the emperors Marcus Aurelius and Lucius Verus. Thamud troops still served Rome in the 5th century.",
            "Islamic tradition identifies the “companions of al-Ḥijr” (15:80) with Thamud and places them at Hegra, also called Madāʾin Ṣāliḥ. More than 110 tombs are cut into its sandstone cliffs; they were carved by the Nabataeans about 2,000 years ago.",
        ],
        stats=[
            ("722–705 BCE", "Reign of Sargon II, whose annals name Thamud"),
            ("165–169 CE", "Ruwāfa temple built by the Thamud"),
            ("110+", "Rock-cut tombs at Hegra (Nabataean)"),
        ],
        media=[
            ("File:27, Hegra (Mada'in Salih), Saudi Arabia.jpg", "A tomb cut into the rock at Hegra (al-Ḥijr), carved by the Nabataeans."),
            ("File:Sargon II and dignitary.jpg", "Sargon II of Assyria, whose annals name Thamud (relief from Dur-Sharrukin, c. 716–713 BCE)."),
        ],
        sources=[
            ("Thamud", "https://en.wikipedia.org/wiki/Thamud"),
            ("Ruwafa inscriptions", "https://en.wikipedia.org/wiki/Ruwafa_inscriptions"),
            ("Hegra", "https://en.wikipedia.org/wiki/Hegra_(Mada%27in_Salih)"),
        ],
    ),
    # ============================ THE COSMOS ============================
    dict(
        id="appointed-term",
        title="The Sun Has an Appointed Term",
        hook="The Sun and the Moon each run “for an appointed term”. Physics found that the Sun's fuel is limited, and measured how long it will last.",
        category="cosmos",
        claimType="interpretive",
        verses=["13:2", "81:1"],
        words=[
            ("يَجْرِى", "yajrī", "runs", "ج ر ي"),
            ("لِأَجَلٍۢ", "li-ajalin", "for a term", "ا ج ل"),
            ("مُّسَمًّۭى", "musamman", "named, appointed", "س م و"),
            ("كُوِّرَتْ", "kuwwirat", "is wrapped up, rolled up", "ك و ر"),
        ],
        science=[
            "Why does the Sun shine, and for how long? Around 1920 Arthur Eddington was the first to suggest, correctly, that stars shine by fusing hydrogen into helium. In 1939 Hans Bethe worked out the nuclear reactions that do it, and won the 1967 Nobel Prize in Physics.",
            "Fusion uses up the Sun's hydrogen. The Sun formed about 4.6 billion years ago; the oldest material in the Solar System is 4.567 billion years old. In about 5 billion years, helium building up in its core will end its steady life. It will swell into a red giant, shed its outer layers and end as a white dwarf that no longer produces energy by fusion.",
            "The Moon's course is changing too. Lasers bounced off mirrors left on the Moon show it spiralling away from the Earth by 3.8 cm a year.",
        ],
        stats=[
            ("4.6 billion years", "The Sun's age so far"),
            ("≈5 billion years", "Until it leaves its steady phase"),
            ("3.8 cm a year", "The Moon's drift away from Earth"),
        ],
        discovery=("1920 – 1939", "Eddington · Bethe"),
        media=[
            ("File:The Sun by the Atmospheric Imaging Assembly of NASA's Solar Dynamics Observatory - 20100819.jpg", "The Sun in ultraviolet light (NASA Solar Dynamics Observatory)."),
            ("File:Sun red giant.svg", "The Sun today, compared with the red giant it will become."),
        ],
        sources=[
            ("Sun: life phases", "https://en.wikipedia.org/wiki/Sun"),
            ("Bethe, “Energy Production in Stars”, Physical Review 55 (1939)", "https://doi.org/10.1103/PhysRev.55.434"),
            ("Lunar Laser Ranging experiments", "https://en.wikipedia.org/wiki/Lunar_Laser_Ranging_experiments"),
        ],
    ),
    dict(
        id="cosmic-web",
        title="A Sky Woven With Paths",
        hook="“By the sky full of pathways (ḥubuk).” Maps of the galaxies show them strung along a vast web of filaments.",
        category="cosmos",
        claimType="interpretive",
        verses=["51:7"],
        words=[
            ("ٱلْحُبُكِ", "al-ḥubuki", "the pathways, the weave, the well-knit structure", "ح ب ك"),
            ("وَٱلسَّمَآءِ", "was-samāʾi", "by the sky", "س م و"),
        ],
        science=[
            "In 1986 Valérie de Lapparent, Margaret Geller and John Huchra mapped the galaxies in a thin slice of sky. Instead of a random scatter, the galaxies lay on the walls of huge bubbles around empty voids. In 1989 Geller and Huchra found the “Great Wall”, a sheet of galaxies stretching across their map.",
            "Bigger surveys and computer simulations, such as the Millennium Simulation of 2005, showed the whole universe is built this way: a “cosmic web” of filaments of galaxies and gas crossing between clusters, with voids in between. Gravity alone pulls matter into this pattern.",
            "The filaments are mostly invisible gas and dark matter. In 2019 a team led by Hideki Umehata captured the faint glow of cosmic-web gas directly, in a young cluster of galaxies whose light set out more than 11 billion years ago.",
        ],
        stats=[
            ("1986", "First “slice of the universe” map"),
            ("1989", "The Great Wall of galaxies"),
            ("2019", "Web gas seen directly"),
        ],
        discovery=("1986 – 2019", "de Lapparent, Geller & Huchra · Springel et al. · Umehata et al."),
        media=[
            ("File:Simulation of large scale structure (eso1438b).jpg", "A simulated cosmic web (Illustris): dark matter in blue, gas in orange, gathered along filaments around voids."),
            ("File:2dfgrs.png", "The 2dF Galaxy Redshift Survey: each dot is a galaxy, and they line up in threads and walls."),
            ("File:Revealing a filament from the cosmic web (potw2504a).jpg", "A filament of the cosmic web imaged by ESO's Very Large Telescope."),
        ],
        sources=[
            ("de Lapparent, Geller & Huchra, “A slice of the universe”, ApJ 302 (1986)", "https://articles.adsabs.harvard.edu/full/1986ApJ...302L...1D"),
            ("Umehata et al., “Gas filaments of the cosmic web”, Science 366 (2019)", "https://doi.org/10.1126/science.aaw5949"),
            ("Large-scale structure of the universe", "https://en.wikipedia.org/wiki/Large-scale_structure_of_the_universe"),
        ],
    ),
    dict(
        id="star-positions",
        title="The Positions of the Stars",
        hook="An oath by “the positions of the stars”, called great “if you only knew”. Astronomers found that no star is quite where it seems.",
        category="cosmos",
        claimType="interpretive",
        verses=["56:75", "56:76"],
        words=[
            ("بِمَوَٰقِعِ", "bi-mawāqiʿi", "by the positions, the places where they fall", "و ق ع"),
            ("ٱلنُّجُومِ", "an-nujūmi", "the stars", "ن ج م"),
            ("عَظِيمٌ", "ʿaẓīmun", "great", "ع ظ م"),
        ],
        science=[
            "The stars look fixed. But in 1718 Edmond Halley showed that Sirius, Arcturus and Aldebaran had moved more than half a degree since the Greek astronomer Hipparchus charted them, some 1,850 years earlier.",
            "In 1727 James Bradley explained why every star seems to shift back and forth over a year, by up to about 20 arcseconds: light travels at a finite speed while the Earth moves around the Sun. Because light takes time, we also see each star where it was when its light set out, years or thousands of years ago.",
            "In 1838 Friedrich Bessel measured the first distance to a star, 61 Cygni. The Gaia space telescope (2013–2025) has since measured the positions and motions of more than a billion stars.",
        ],
        stats=[
            ("1718", "Halley shows the “fixed” stars move"),
            ("≈20″", "Yearly shift of every star (aberration)"),
            ("1.7 billion", "Stars in Gaia's 2018 map"),
        ],
        discovery=("1718 – 1838", "Halley · Bradley · Bessel"),
        media=[
            ("File:Gaia's all-sky view (ESA Gaia DR2 AllSky Brightness Colour black bg 8k).jpg", "Gaia's map of our galaxy, built from measurements of nearly 1.7 billion stars (ESA)."),
            ("File:James Bradley.jpg", "James Bradley, who discovered the aberration of starlight."),
            ("File:Friedrich Wilhelm Bessel (1839 painting).jpg", "Friedrich Bessel, who measured the first distance to a star."),
        ],
        sources=[
            ("Proper motion", "https://en.wikipedia.org/wiki/Proper_motion"),
            ("Aberration (astronomy)", "https://en.wikipedia.org/wiki/Aberration_(astronomy)"),
            ("Gaia (spacecraft)", "https://en.wikipedia.org/wiki/Gaia_(spacecraft)"),
        ],
    ),
    dict(
        id="smaller-than-atom",
        title="Smaller Than an Atom's Weight",
        hook="“Not an atom's weight… nor anything smaller than that.” The Greek word atom means “uncuttable”, until physics found smaller things inside it.",
        category="cosmos",
        claimType="interpretive",
        verses=["10:61", "34:3"],
        words=[
            ("مِّثْقَالِ", "mithqāli", "the weight of", "ث ق ل"),
            ("ذَرَّةٍۢ", "dharratin", "a speck, a tiny ant (today: an atom)", "ذ ر ر"),
            ("أَصْغَرَ", "aṣghara", "smaller", "ص غ ر"),
        ],
        science=[
            "Greek thinkers such as Democritus imagined that matter is made of tiny “atoms”, a word that means “uncuttable”. When John Dalton revived the idea in the early 1800s, atoms were still thought to be the smallest units of matter.",
            "Then they were cut. In 1897 J. J. Thomson discovered the electron, far lighter than any atom. In 1911 Ernest Rutherford showed that nearly all of an atom's mass sits in a nucleus about 27,000 to 60,000 times narrower than the atom itself. The proton was named in 1920 and the neutron was discovered in 1932.",
            "In 1968 experiments at the Stanford Linear Accelerator Center showed that protons themselves contain smaller, point-like particles, now called quarks.",
        ],
        stats=[
            ("1897", "The electron: first particle smaller than an atom"),
            ("27,000–60,000×", "How much narrower the nucleus is than the atom"),
            ("1968", "Quarks seen inside the proton"),
        ],
        discovery=("1897 – 1968", "Thomson · Rutherford · Chadwick · SLAC"),
        media=[
            ("File:Cloud Chamber Photo of Heavy Nucleus.jpg", "The track of an atomic nucleus from space, caught in a cloud chamber (1960)."),
            ("File:Rutherford gold foil experiment results.svg", "Rutherford's result: most particles pass straight through, a few bounce off a tiny nucleus."),
            ("File:Ernest Rutherford LOC.jpg", "Ernest Rutherford."),
        ],
        sources=[
            ("Atom", "https://en.wikipedia.org/wiki/Atom"),
            ("Atomic nucleus", "https://en.wikipedia.org/wiki/Atomic_nucleus"),
            ("Quark", "https://en.wikipedia.org/wiki/Quark"),
        ],
    ),
    # ============================ EARTH & SEAS ============================
    dict(
        id="sea-fire",
        title="The Sea Set Aflame",
        hook="An oath “by the sea set aflame”. In 1977 scientists found hot springs on the ocean floor; some vent water hotter than 400 °C.",
        category="earth",
        claimType="interpretive",
        verses=["52:6", "81:6"],
        words=[
            ("ٱلْمَسْجُورِ", "al-masjūri", "set aflame, heated; also: filled", "س ج ر"),
            ("سُجِّرَتْ", "sujjirat", "are set aflame, made to overflow", "س ج ر"),
            ("ٱلْبِحَارُ", "al-biḥāru", "the seas", "ب ح ر"),
        ],
        science=[
            "Along the mid-ocean ridges, a chain of undersea mountains 65,000 km long, the Earth's crust is pulling apart and hot rock rises close to the seafloor. In February 1977 geologists diving in the submersible Alvin found warm springs on the Galápagos Rift, surrounded by giant tube worms, clams and whole communities living in total darkness.",
            "In April 1979, on the East Pacific Rise, Alvin found chimneys jetting black, mineral-laden water: the first “black smokers”. Vent water can be hotter than 400 °C, and a brief 464 °C has been measured. It does not boil because of the crushing pressure of the deep. Lava erupts on the seafloor along the ridges as well.",
        ],
        stats=[
            ("1977", "First vents found, Galápagos Rift"),
            ("1979", "First black smokers, East Pacific Rise"),
            ("464 °C", "Hottest vent water measured"),
        ],
        discovery=("1977 – 1979", "Corliss & van Andel · Spiess, Macdonald et al. (Alvin)"),
        media=[
            ("File:Blacksmoker in Atlantic Ocean.jpg", "A black smoker on a mid-ocean ridge in the Atlantic (NOAA)."),
            ("File:Chimney-hires.jpg", "A 255 °C vent near the top of a 38-metre sulfide chimney in the Pacific (NOAA)."),
            ("File:ALVIN submersible.jpg", "The submersible Alvin in 1978, a year after it found the first vents."),
        ],
        sources=[
            ("Hydrothermal vent", "https://en.wikipedia.org/wiki/Hydrothermal_vent"),
            ("Corliss et al., “Submarine Thermal Springs on the Galápagos Rift”, Science 203 (1979)", "https://doi.org/10.1126/science.203.4385.1073"),
            ("Spiess et al., “East Pacific Rise: Hot Springs and Geophysical Experiments”, Science 207 (1980)", "https://doi.org/10.1126/science.207.4438.1421"),
        ],
    ),
    dict(
        id="earth-quivers",
        title="The Earth Stirs When Rain Falls",
        hook="“You see the earth stilled; then when We send down rain on it, it stirs and swells.” Soil scientists have measured both.",
        category="earth",
        claimType="interpretive",
        verses=["41:39"],
        words=[
            ("خَٰشِعَةًۭ", "khāshiʿatan", "stilled, humbled", "خ ش ع"),
            ("ٱهْتَزَّتْ", "ihtazzat", "it stirs, it quivers", "ه ز ز"),
            ("وَرَبَتْ", "wa-rabat", "and it swells", "ر ب و"),
        ],
        science=[
            "When rain falls on dry ground, the ground really moves. Clay soils soak up water and swell, lifting the surface, and seeds drink water and swell before they sprout.",
            "Life in the soil wakes up too. In 1958 H. F. Birch showed that wetting dried soil sets off a sudden burst of microbial activity, releasing carbon dioxide and nitrogen. Soil scientists now call it the Birch effect.",
            "In 2015 Youngsoo Joung and Cullen Buie filmed raindrops hitting soil at high speed: each drop traps tiny bubbles of air that burst out of the ground as a fine mist of aerosols. The smell of rain on dry earth was named petrichor by Isabel Bear and Richard Thomas in 1964.",
        ],
        stats=[
            ("1958", "The Birch effect described"),
            ("1964", "Petrichor named"),
            ("2015", "Raindrop aerosols filmed"),
        ],
        discovery=("1958 – 2015", "H. F. Birch · Bear & Thomas · Joung & Buie"),
        media=[
            ("File:Water and soil splashed by the impact of a single raindrop.jpg", "A single raindrop hitting the soil (USDA)."),
        ],
        sources=[
            ("Birch, “The effect of soil drying on humus decomposition”, Plant and Soil 10 (1958)", "https://doi.org/10.1007/BF01343734"),
            ("Joung & Buie, “Aerosol generation by raindrop impact on soil”, Nature Communications 6 (2015)", "https://doi.org/10.1038/ncomms7083"),
            ("Petrichor", "https://en.wikipedia.org/wiki/Petrichor"),
        ],
    ),
    # ============================ HUMAN CREATION ============================
    dict(
        id="altitude-breath",
        title="Breathless, as if Climbing Into the Sky",
        hook="A heart closed to guidance feels “tight and constricted, as though climbing into the sky”. The higher you climb, the less air there is to breathe.",
        category="human",
        claimType="interpretive",
        verses=["6:125"],
        words=[
            ("ضَيِّقًا", "ḍayyiqan", "tight, narrow", "ض ي ق"),
            ("حَرَجًۭا", "ḥarajan", "constricted", "ح ر ج"),
            ("يَصَّعَّدُ", "yaṣṣaʿʿadu", "climbs with great effort", "ص ع د"),
        ],
        science=[
            "Air has weight. Evangelista Torricelli made the first barometer in 1643, and in 1648 Florin Périer, at Blaise Pascal's urging, carried one up the Puy de Dôme in France. The higher he climbed, the lower the mercury stood: the air thins with height.",
            "In 1878 Paul Bert showed that the harm of altitude comes from the low pressure of oxygen. At 5,000 metres the air pressure is about half its sea-level value, and at the top of Mount Everest only about a third. Breathing speeds up and the chest labours; above 5,500 metres, which doctors call extreme altitude, blood oxygen falls sharply.",
        ],
        stats=[
            ("1648", "Air pressure falls with height (Pascal & Périer)"),
            ("½", "Air pressure at 5,000 m, compared with sea level"),
            ("⅓", "Air pressure on the summit of Everest"),
        ],
        discovery=("1648 – 1878", "Torricelli · Pascal & Périer · Paul Bert"),
        media=[
            ("File:Mount Everest as seen from Drukair2 PLW edit Cropped.jpg", "Mount Everest. At its summit the air has about a third of its sea-level pressure."),
            ("File:FR631136102- Elévation de la montagne du Puy de Domme - GRA 3055.jpg", "An old engraving of Pascal's Puy de Dôme experiment."),
            ("File:Paul Bert 01.jpg", "Paul Bert, who found that altitude sickness is caused by lack of oxygen."),
        ],
        sources=[
            ("Effects of high altitude on humans", "https://en.wikipedia.org/wiki/Effects_of_high_altitude_on_humans"),
            ("West et al., “Barometric pressures at extreme altitudes on Mt. Everest”, J. Appl. Physiol. 54 (1983)", "https://doi.org/10.1152/jappl.1983.54.5.1188"),
            ("Altitude sickness", "https://en.wikipedia.org/wiki/Altitude_sickness"),
        ],
    ),
    # ============================ LIVING THINGS ============================
    dict(
        id="green-fire",
        title="Fire From the Green Tree",
        hook="“He made fire for you from the green tree.” Every burning log releases sunlight that green leaves once captured.",
        category="life",
        claimType="interpretive",
        verses=["36:80", "56:71", "56:72"],
        words=[
            ("ٱلشَّجَرِ", "ash-shajari", "the trees", "ش ج ر"),
            ("ٱلْأَخْضَرِ", "al-akhḍari", "the green", "خ ض ر"),
            ("نَارًۭا", "nāran", "fire", "ن و ر"),
            ("تُورُونَ", "tūrūna", "you kindle", "و ر ي"),
        ],
        science=[
            "In 1779 Jan Ingenhousz found that plants give off bubbles of oxygen from their green parts only in sunlight: the discovery of photosynthesis. Julius Robert Mayer then proposed that plants turn the energy of light into chemical energy.",
            "Leaves are green because of chlorophyll, which captures sunlight inside chloroplasts. With that energy, plants build sugars from carbon dioxide and water; Melvin Calvin traced the path of the carbon and won the 1961 Nobel Prize in Chemistry.",
            "Wood is built from those sugars. When it burns, the stored energy of sunlight comes back out as heat and light. Photosynthesis around the world captures energy at about 130 terawatts, some eight times the power used by all of human civilisation.",
        ],
        stats=[
            ("1779", "Photosynthesis discovered (Ingenhousz)"),
            ("1961", "Nobel Prize for the path of carbon (Calvin)"),
            ("≈130 TW", "Power captured by photosynthesis worldwide"),
        ],
        discovery=("1779 – 1961", "Ingenhousz · Mayer · Calvin"),
        media=[
            ("File:Leaf 1 web.jpg", "A green leaf: a solar panel that stores energy in wood."),
            ("File:Leaf palisade mesophyll.jpg", "Leaf cells packed with green chloroplasts, where sunlight is captured."),
            ("File:Jan Ingenhousz.jpg", "Jan Ingenhousz, who discovered photosynthesis."),
        ],
        sources=[
            ("Photosynthesis", "https://en.wikipedia.org/wiki/Photosynthesis"),
            ("Jan Ingenhousz", "https://en.wikipedia.org/wiki/Jan_Ingenhousz"),
            ("Nobel Prize in Chemistry 1961: Melvin Calvin", "https://www.nobelprize.org/prizes/chemistry/1961/calvin/facts/"),
        ],
    ),
    dict(
        id="crow-burial",
        title="The Crow That Dug the Ground",
        hook="In the Quran, the first burial is taught by a crow scratching the ground. Crows turned out to be among the cleverest animals on Earth.",
        category="life",
        claimType="interpretive",
        verses=["5:31"],
        words=[
            ("غُرَابًۭا", "ghurāban", "a crow, a raven", "غ ر ب"),
            ("يَبْحَثُ", "yabḥathu", "scratching, digging, searching", "ب ح ث"),
            ("يُوَٰرِى", "yuwārī", "to cover, to hide", "و ر ي"),
        ],
        science=[
            "Crows, ravens and jays, the corvid family, are among the most intelligent animals known. In 1996 Gavin Hunt reported that wild New Caledonian crows make hooked tools from twigs and leaves. In 2002 a crow named Betty bent a straight wire into a hook to lift food out of a tube.",
            "Corvids hide food in the ground and remember it. In 1998 Nicola Clayton and Anthony Dickinson showed that scrub jays remember what they hid, where, and how long ago. Wild American crows remember the faces of people who threatened them, and they gather around a dead crow to learn about danger.",
        ],
        stats=[
            ("1996", "Crows making hooked tools (Hunt)"),
            ("1998", "Jays remember what, where and when"),
            ("2015", "Crows gather around their dead"),
        ],
        discovery=("1996 – 2015", "Hunt · Clayton & Dickinson · Weir & Kacelnik · Marzluff"),
        media=[
            ("File:Corvus moneduloides eating on branch.jpg", "A New Caledonian crow, a species that makes its own tools."),
            ("File:2014-04-29 01 Northwestern crow (Corvus brachyrhynchos caurinus).jpg", "An American crow."),
        ],
        sources=[
            ("Hunt, “Manufacture and use of hook-tools by New Caledonian crows”, Nature 379 (1996)", "https://doi.org/10.1038/379249a0"),
            ("Clayton & Dickinson, “Episodic-like memory during cache recovery by scrub jays”, Nature 395 (1998)", "https://doi.org/10.1038/26216"),
            ("Swift & Marzluff, “Wild American crows gather around their dead”, Animal Behaviour 109 (2015)", "https://doi.org/10.1016/j.anbehav.2015.08.021"),
        ],
    ),
]
