# -*- coding: utf-8 -*-
"""Evidence (proof timelines, fit / gaps) for the facts in facts_third.py. Same format as facts_evidence.py."""

THIRD_EVIDENCE = {
    # ---------------------------------------------------------------- SIGNS & HISTORY
    "oldest-manuscripts": dict(
        proofHeading="How the pages were dated",
        proof=[
            ("1972", "A hidden cache in Sanaa",
             "Workers repairing the attic of the Great Mosque of Sanaa, Yemen, find large quantities of old parchment. "
             "By 1997 the fragments had been sorted into 926 separate Quran manuscripts.", None, None),
            ("2010", "The Sanaa palimpsest is dated",
             "Behnam Sadeghi and Uwe Bergmann publish a radiocarbon date for one of its leaves: 578–669 CE with 95% probability. "
             "Its visible upper text is the standard text; an older, erased lower text differs from it in places.",
             "File:Sana'a1 Stanford '07 recto.jpg", "A leaf of the Sanaa palimpsest."),
            ("2014", "Tübingen",
             "The University of Tübingen announces that its Quran fragment Ma VI 165 dates to 649–675 CE (95.4%).", None, None),
            ("2015", "Birmingham",
             "While studying the Mingana Collection for her PhD, Alba Fedeli notices two leaves in an early script. "
             "Oxford's Radiocarbon Accelerator Unit measures 1465 ± 21 radiocarbon years: 568–645 CE with 95.4% probability.",
             "File:Birmingham Quran manuscript.jpg", "The two Birmingham leaves."),
            ("2015", "Part of a Paris codex",
             "François Déroche confirms that the Birmingham leaves belong with 16 leaves in Paris, now bound with the Codex Parisino-petropolitanus.",
             "File:Codex Parisino-petropolitanus, first leaf recto.jpg", "The Codex Parisino-petropolitanus."),
        ],
        fit=dict(
            fits=[
                "Several manuscripts from the first Islamic century survive, and their text is recognisably the Quran recited today.",
                "The Birmingham leaves have their verses in today's order, and Marijn van Putten has shown they share the spelling quirks of the ʿUthmānic text.",
                "The verse promises the Reminder will be guarded, and copies from its first decades support the tradition that the text was fixed early.",
            ],
            gaps=[
                "Radiocarbon dates the death of the animal, not the day of writing. Parchment could be stored first, so the Birmingham text may have been written as late as the 650s.",
                "Some specialists, including François Déroche, doubt how precise radiocarbon dates of Qurans are.",
                "The erased lower text of the Sanaa palimpsest has real differences from the standard text, including extra words and a different order of surahs.",
                "The earliest copies have no vowel marks and few dots. They preserve the consonant outline; the exact recitation was passed on orally.",
            ],
        ),
    ),
    "saba-dam": dict(
        proofHeading="What the inscriptions and ruins record",
        proof=[
            ("c. 760–740 BCE", "The first inscription",
             "An inscription placed on the dam during building or repair names the Sabaean ruler Yathaʿ ʾAmar Watar I.",
             "File:Marib dam.jpg", "Remains of the ancient dam of Maʾrib."),
            ("c. 500 BCE", "Two gardens",
             "The dam is raised to 7 metres and faced with stone, and irrigation is extended to the southern side of the valley as well as the northern: "
             "two oases, one on each side.", None, None),
            ("449–557 CE", "Breaches and repairs",
             "Inscriptions record major breaches in 449, 450, 542 and 548, the workers and the costs of repair, and a last repair in 557. "
             "The ruler Abraha repairs much of Maʾrib's works around 548.", None, None),
            ("570 or 575 CE", "The last flood",
             "The water overtops the dam again, and it is left unrepaired. The irrigation system fails and many people migrate across Arabia.",
             "File:Ancient Ma'rib 01.jpg", "Ruins of ancient Maʾrib."),
            ("2023", "World Heritage",
             "UNESCO lists the landmarks of the ancient Kingdom of Saba, Maʾrib, as World Heritage, and at once as in danger because of the war in Yemen.",
             None, None),
        ],
        fit=dict(
            fits=[
                "Saba' was a real kingdom centred on Maʾrib, and its dam was one of the engineering wonders of the ancient world.",
                "The watered land lay in two oases on either side of the valley: “two gardens, on the right and on the left”.",
                "The dam did fail and was abandoned, and its gardens went back to desert scrub.",
            ],
            gaps=[
                "Which breach the verse means is debated: a major breach in about 145 BCE, or the final one in 570 or 575.",
                "The fall of Saba' was well known to Arabs in the Prophet's ﷺ time. This is history that checks out, not hidden knowledge.",
                "The verses give a moral reason (they turned away). Archaeology can confirm the flood, not the reason.",
            ],
        ),
    ),
    "thamud": dict(
        proofHeading="Where Thamud appear in the records",
        proof=[
            ("722–705 BCE", "Sargon II's annals",
             "The Assyrian king's annals, inscribed at Dur-Sharrukin, list Thamud among Arab tribes he says he defeated and moved to Samaria. "
             "The historian Israel Ephʿal doubts the campaign happened as told, but the name is there.",
             "File:Sargon II and dignitary.jpg", "Sargon II of Assyria."),
            ("1st c. BCE – 2nd c. CE", "Greek and Roman writers",
             "Diodorus places the Thamudeni on the Red Sea coast of Arabia; Pliny and Ptolemy mention them too.", None, None),
            ("1910", "The Ruwāfa temple",
             "Alois Musil finds a temple in the Hisma desert with inscriptions in Greek and Nabataean. Dated to 165–169 CE, "
             "they say the Thamud built it under Roman rule.", None, None),
            ("2008", "Hegra, World Heritage",
             "UNESCO makes Hegra (al-Ḥijr, Madāʾin Ṣāliḥ) Saudi Arabia's first World Heritage Site. Its rock-cut tombs are Nabataean.",
             "File:27, Hegra (Mada'in Salih), Saudi Arabia.jpg", "A rock-cut tomb at Hegra."),
        ],
        fit=dict(
            fits=[
                "Thamud were a real people of north-west Arabia, named in records from the 8th century BCE to the 5th century CE.",
                "Their region, around al-Ḥijr, is full of rooms and tombs cut into rock: the kind of place the verses describe.",
            ],
            gaps=[
                "The famous tombs at Hegra were carved by the Nabataeans, not by the older Thamud. Archaeologists have not identified Thamud's own rock houses.",
                "Sargon II's account may be exaggerated, and the destruction of Thamud is told only in scripture.",
                "Arabs in the Prophet's ﷺ time passed Hegra on the caravan road, so Thamud's name was known. This is history that checks out, not hidden knowledge.",
            ],
        ),
    ),
    # ---------------------------------------------------------------- THE COSMOS
    "appointed-term": dict(
        proof=[
            ("c. 1920", "Eddington: hydrogen into helium",
             "In “The Internal Constitution of the Stars”, Arthur Eddington suggests the Sun shines by fusing hydrogen into helium. "
             "At the time the source of starlight was a complete mystery.",
             "File:Arthur Stanley Eddington.jpg", "Arthur Eddington."),
            ("1939", "Bethe: the reactions",
             "Hans Bethe works out the nuclear reactions that power the Sun and other stars (Physical Review 55, 434). He wins the 1967 Nobel Prize.",
             "File:Hans Bethe.jpg", "Hans Bethe."),
            ("1969 onwards", "Measuring the Moon",
             "Lasers fired at reflectors left on the Moon, starting with Apollo 11, time the round trip of light and show the Moon spiralling away by 3.8 cm a year.",
             None, None),
            ("2012", "The age of the Solar System",
             "Lead isotopes in the oldest grains of meteorites give an age of 4.567 billion years (Connelly et al., Science 338).", None, None),
        ],
        fit=dict(
            fits=[
                "“Each runs for an appointed term”: the Sun has a limited supply of fuel and a lifetime that can be calculated.",
                "81:1 speaks of the Sun being “wrapped up”, and physics expects the Sun's light as we know it to end.",
            ],
            gaps=[
                "Classical commentators read the “term” as the Day of Judgement, or as the Sun's daily and yearly course.",
                "81:1 describes the Last Day, not a red giant billions of years from now. Nothing in the verses gives a timescale.",
                "That the Sun and Moon will not last forever is a belief of many religions and philosophies, not only a result of physics.",
            ],
        ),
    ),
    "cosmic-web": dict(
        proof=[
            ("1986", "A slice of the universe",
             "de Lapparent, Geller and Huchra map the galaxies in a thin wedge of sky. They sit on the walls of bubbles around empty voids "
             "(Astrophysical Journal 302, L1).", None, None),
            ("1989", "The Great Wall",
             "Geller and Huchra report a vast wall of galaxies stretching across their map (Science 246, 897).", None, None),
            ("2005", "The Millennium Simulation",
             "Volker Springel and colleagues follow 10 billion particles of dark matter in a supercomputer. Gravity draws them into a web of filaments "
             "(Nature 435, 629).",
             "File:Cosmic web.jpg", "The cosmic web in the Millennium Simulation (Springel, Max Planck Institute for Astrophysics)."),
            ("2019", "The web seen",
             "Hideki Umehata and colleagues detect glowing gas in filaments that link galaxies in the young cluster SSA22 (Science 366, 97).",
             None, None),
        ],
        fit=dict(
            fits=[
                "Ḥubuk means paths or tracks, and also a weave or well-knit structure: the galaxies lie along filaments like threads.",
                "The verse swears by the structure of the sky, and the large-scale structure of the universe was unknown until the 1980s.",
            ],
            gaps=[
                "Classical commentators read ḥubuk as the sky's beauty and fine making, the courses of the stars, or ripples like wind on sand.",
                "The cosmic web can only be seen with telescopes and computers. The verse speaks to people looking up at the night sky.",
                "“Paths” could describe many things in the sky, so the match is suggestive rather than specific.",
            ],
        ),
    ),
    "star-positions": dict(
        proof=[
            ("1718", "Halley: the fixed stars move",
             "Edmond Halley compares his star positions with the ancient catalogue of Hipparchus and finds Sirius, Arcturus and Aldebaran "
             "displaced by more than half a degree.", None, None),
            ("1727", "Bradley: aberration",
             "James Bradley explains a yearly back-and-forth shift in star positions: light's finite speed combined with the Earth's motion tilts where stars appear.",
             "File:James Bradley.jpg", "James Bradley."),
            ("1838", "Bessel: the first distance to a star",
             "Friedrich Bessel measures the parallax of 61 Cygni, the first reliable distance to a star.",
             "File:Friedrich Wilhelm Bessel (1839 painting).jpg", "Friedrich Bessel."),
            ("2013–2025", "Gaia",
             "ESA's Gaia space telescope maps the positions, distances and motions of more than a billion stars.",
             "File:Gaia's all-sky view (ESA Gaia DR2 AllSky Brightness Colour black bg 8k).jpg", "Gaia's all-sky map."),
        ],
        fit=dict(
            fits=[
                "Mawāqiʿ means the places where things fall or are set. Astronomers found that the positions we see are shifted by motion and by the time light takes to arrive.",
                "The verse calls the oath great “if you only knew”, hinting there is more to the stars' positions than meets the eye.",
            ],
            gaps=[
                "Classical commentators read mawāqiʿ as the places where the stars set, or their stations in the sky. Some early commentators read nujūm as the parts in which the Quran was revealed.",
                "The verse does not mention motion, light or distance. The link rests on the reading of one word.",
            ],
        ),
    ),
    "smaller-than-atom": dict(
        proof=[
            ("1897", "Thomson: the electron",
             "J. J. Thomson shows that cathode rays are streams of particles much lighter than any atom: the first thing found to be smaller.", None, None),
            ("1909–1911", "Rutherford: the nucleus",
             "Hans Geiger and Ernest Marsden fire alpha particles at gold foil and a few bounce straight back. Rutherford concludes that the atom's mass sits in a tiny nucleus.",
             "File:Rutherford gold foil experiment results.svg", "Expected and observed results of the gold foil experiment."),
            ("1932", "Chadwick: the neutron",
             "James Chadwick discovers the neutron, a particle distinct from the proton, completing the picture of the nucleus.", None, None),
            ("1968", "SLAC: quarks",
             "Electrons fired at protons at the Stanford Linear Accelerator Center bounce off smaller, point-like particles inside them: quarks.",
             None, None),
        ],
        fit=dict(
            fits=[
                "The verses name the dharra and then go further, to “anything smaller than that”. Matter turned out to have smaller and smaller parts.",
                "The phrase appears in more than one verse (10:61 and 34:3), always stressing that nothing is too small for God's knowledge.",
            ],
            gaps=[
                "In classical Arabic, dharra meant a tiny ant or a speck of dust in a sunbeam. It came to mean “atom” later, so early readers did not hear a physics claim.",
                "The verses are about God's knowledge, not the structure of matter. “Smaller than that” says only that nothing is too small to be known.",
                "Ideas about the smallest particles are much older than Islam, in Greek and Indian philosophy, and Muslim theologians debated atoms too.",
            ],
        ),
    ),
    # ---------------------------------------------------------------- EARTH & SEAS
    "sea-fire": dict(
        proof=[
            ("1976", "First signs",
             "A Scripps Institution of Oceanography expedition towing cameras over the Galápagos Rift finds the first evidence of hot springs on the seafloor.",
             None, None),
            ("1977", "Vents full of life",
             "On 17 February 1977 Jack Corliss and Tjeerd van Andel dive in Alvin and see the vents and the life around them (Science 203, 1073).",
             "File:ALVIN submersible.jpg", "The submersible Alvin."),
            ("1979", "Black smokers",
             "On 21 April 1979, on the East Pacific Rise at 21° N, William Normark and Thierry Juteau find chimneys jetting black particles. "
             "A temperature probe is rigged to measure how hot they are (Science 207, 1421).",
             "File:Blacksmoker in Atlantic Ocean.jpg", "A black smoker."),
            ("2008", "Loki's Castle",
             "Scientists from the University of Bergen find black smokers at 73° N, between Greenland and Norway: the most northerly known.", None, None),
        ],
        fit=dict(
            fits=[
                "Masjūr and sujjirat come from a root used for firing an oven. Beneath the sea there are vents of scalding water and erupting lava.",
                "The vents lie under kilometres of dark water and were unknown until 1977.",
            ],
            gaps=[
                "Classical commentators give several meanings: “filled”, “held back”, or “set on fire on the Last Day”. 81:6 describes the end of the world.",
                "There are no flames under the sea. Vent water is scalding but does not burn, and lava cools almost at once.",
                "Volcanic islands erupting from the sea were seen long before, for example Thera in the Aegean.",
            ],
        ),
    ),
    "earth-quivers": dict(
        proof=[
            ("1958", "Birch: the soil wakes up",
             "H. F. Birch finds that when dried soil is wetted again, its microbes release a burst of carbon dioxide and nitrogen (Plant and Soil 10, 9).",
             None, None),
            ("1964", "Petrichor",
             "Isabel Bear and Richard Thomas of Australia's CSIRO extract the oily source of the smell of rain from dry rocks and soil, and name it petrichor "
             "(Nature 201, 993).", None, None),
            ("2015", "Raindrops filmed",
             "Youngsoo Joung and Cullen Buie at MIT film raindrops landing on soils at high speed. The drops release aerosols from the ground "
             "(Nature Communications 6, 6083).",
             "File:Water and soil splashed by the impact of a single raindrop.jpg", "A raindrop hitting soil."),
        ],
        fit=dict(
            fits=[
                "Ihtazzat (it stirs) and rabat (it swells) describe what rain does to dry ground: it swells, gives off gas and bursts into life.",
                "The verse compares this to bringing the dead back to life, and dry soil does come alive with microbes and seedlings.",
            ],
            gaps=[
                "Anyone who watched rain fall on dry land saw it soften, swell and turn green. The first listeners did not need a microscope.",
                "Classical commentators read the words as the earth moving with sprouting plants and swelling with growth.",
                "The Birch effect and raindrop aerosols are modern details that the verse does not mention.",
            ],
        ),
    ),
    # ---------------------------------------------------------------- HUMAN CREATION
    "altitude-breath": dict(
        proof=[
            ("1643", "Torricelli's barometer",
             "Evangelista Torricelli balances a column of mercury against the weight of the air: the first barometer.", None, None),
            ("1648", "Up the Puy de Dôme",
             "Florin Périer, urged by his brother-in-law Blaise Pascal, measures the mercury at three heights on the mountain. The higher he goes, the lower it stands.",
             "File:FR631136102- Elévation de la montagne du Puy de Domme - GRA 3055.jpg", "An engraving of the Puy de Dôme experiment."),
            ("1878", "Bert: it is the oxygen",
             "Paul Bert's La Pression barométrique shows that the harm of altitude comes from the low pressure of oxygen.",
             "File:Paul Bert 01.jpg", "Paul Bert."),
            ("1981", "Measured on Everest",
             "Members of the American Medical Research Expedition to Everest measure the air pressure on the summit (West et al., J. Appl. Physiol. 1983).",
             None, None),
        ],
        fit=dict(
            fits=[
                "Yaṣṣaʿʿadu describes climbing with great effort, and a tight, constricted chest is how climbing into thin air feels.",
                "The comparison points upward, “into the sky”, where the air does grow thinner.",
            ],
            gaps=[
                "The verse is a simile for a heart closed to faith, not a statement about air pressure.",
                "People knew mountain breathlessness long before: a Chinese text from about 30 BCE describes the “Big Headache Mountains”, and Arabia has high mountains too.",
                "Some commentators read “climbing into the sky” as attempting the impossible, a common image for something one cannot do.",
            ],
        ),
    ),
    # ---------------------------------------------------------------- LIVING THINGS
    "green-fire": dict(
        proof=[
            ("1779", "Ingenhousz: light is the key",
             "At a country house near London, Jan Ingenhousz shows that plants under water give off bubbles from their green parts in sunlight, and stop in the shade.",
             "File:Jan Ingenhousz.jpg", "Jan Ingenhousz."),
            ("1840s", "Mayer: light into chemical energy",
             "Julius Robert Mayer proposes that plants turn the energy of light into chemical energy.", None, None),
            ("1950s", "Calvin: following the carbon",
             "Melvin Calvin traces how plants turn carbon dioxide into sugar, step by step. He wins the 1961 Nobel Prize in Chemistry.", None, None),
        ],
        fit=dict(
            fits=[
                "The verse ties fire to the green tree, and the energy in burning wood was captured from sunlight by green leaves.",
                "56:71–72 asks who produced the tree behind your fire, pointing to the tree as the true source of the fire.",
            ],
            gaps=[
                "Classical commentators explain the verse by the markh and ʿafār trees, whose green branches Arabs rubbed together to make fire. The plain meaning was familiar.",
                "Every wood fire comes from a tree, so the verse needs no hidden science to be true.",
                "Chlorophyll, sugar and stored energy are not mentioned in the verses.",
            ],
        ),
    ),
    "crow-burial": dict(
        proof=[
            ("1996", "Tool makers",
             "Gavin Hunt describes wild New Caledonian crows making hooked tools from twigs and leaves (Nature 379, 249).",
             "File:Corvus moneduloides eating on branch.jpg", "A New Caledonian crow."),
            ("1998", "Hidden food, remembered",
             "Nicola Clayton and Anthony Dickinson show that scrub jays remember what food they hid, where, and how long ago (Nature 395, 272).",
             None, None),
            ("2002", "Betty bends a wire",
             "At Oxford, Alex Weir, Jackie Chappell and Alex Kacelnik watch a crow bend a straight wire into a hook to reach food (Science 297, 981).",
             None, None),
            ("2015", "Crows at their dead",
             "Kaeli Swift and John Marzluff find that wild American crows gather around a dead crow, and afterwards avoid the place and the people linked to it "
             "(Animal Behaviour 109, 187).",
             "File:2014-04-29 01 Northwestern crow (Corvus brachyrhynchos caurinus).jpg", "An American crow."),
        ],
        fit=dict(
            fits=[
                "The verse shows a crow scratching the ground as a teacher. Crows do dig and hide things in the ground, and are skilled learners and problem-solvers.",
                "Crows react to their own dead, the very situation in the story.",
            ],
            gaps=[
                "The verse tells a story about the sons of Adam. It does not claim that crows are intelligent.",
                "People have always seen crows digging and hiding food; that part was never hidden.",
                "Commentators say the crow in the story buried another dead crow. Wild crows have not been seen doing that, although they do gather around their dead.",
            ],
        ),
    ),
}
