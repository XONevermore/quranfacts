# -*- coding: utf-8 -*-
"""
The "how was it proven?" and "how well does it fit?" content for each fact, keyed by fact id.
Merged into facts.json by build_content.py.

proof   : ordered steps. (year, title, text, commons_file_or_None, caption_or_None)
          Say what was actually done and measured, by whom, and what it showed.
          Only include people, dates and numbers that have been checked.
fit     : where the verse's wording matches the finding ("fits"), and where it does not, or where
          the reading is contested ("gaps"). Every fact gets both: an honest match needs both.
sounds  : audio clips (Commons files) that help explain the science.
proofHeading : optional override for the section title.
"""

EVIDENCE = {
    # ------------------------------------------------------------------ COSMOS
    "big-bang": dict(
        proofHeading="How it was worked out",
        proof=[
            ("1927", "Lemaître: the universe must be expanding",
             "The Belgian priest and physicist Georges Lemaître shows from Einstein's equations that the universe cannot be static, and estimates the rate of expansion from galaxy data. In 1931 he proposes it began as a single “primeval atom”.",
             None, None),
            ("1929", "Hubble: the farther, the faster",
             "Edwin Hubble plots the speed of galaxies against their distance and finds a straight line. Run backwards, everything converges.",
             "File:Hubble's law original 1929.png",
             "Hubble's original 1929 plot: each dot is a galaxy, the farther away, the faster it recedes."),
            ("1948", "Alpher and Herman: a predicted afterglow",
             "Ralph Alpher and Robert Herman calculate that a hot early universe would leave a faint microwave glow, a few kelvin above absolute zero.",
             None, None),
            ("1965", "Penzias and Wilson: they hear it",
             "At Bell Labs, Arno Penzias and Robert Wilson cannot get rid of a faint hiss in their horn antenna, from every direction, day and night: the afterglow, about 3.5 K. Nobel Prize 1978.",
             "File:Bell Labs Horn Antenna Crawford Hill NJ.jpg",
             "The Holmdel horn antenna where the afterglow of the Big Bang was found."),
            ("1990–2018", "COBE, WMAP and Planck map it",
             "Satellites measure the glow: a near-perfect 2.725 K with tiny ripples that grew into galaxies. Planck fixes the age of the universe at about 13.8 billion years.",
             "File:Cosmic Microwave Background (CMB).jpeg",
             "The afterglow of the early universe, mapped by ESA's Planck satellite."),
        ],
        fit=dict(
            fits=[
                "One joined mass that was then separated matches a single hot, dense state that expanded and cooled.",
                "41:11 has the heaven as “smoke” before it took shape, matching the hot gas stage before the first stars.",
            ],
            gaps=[
                "No date, temperature or mechanism is given.",
                "The Earth formed about 9 billion years after the Big Bang, so “the heavens and the earth” cannot mean our planet literally.",
                "The Big Bang is the expansion of space, not an explosion into space; “split” is a picture, not physics.",
            ],
        ),
        sounds=[
            ("File:The Sound of the Big Bang (100 seconds).ogg",
             "Listen: physicist John Cramer turned the ripples in the afterglow into sound, raised about 100 septillion times in pitch so ears can hear it. A sonification, not a recording."),
        ],
    ),
    "expanding-universe": dict(
        proof=[
            ("1912–1925", "Slipher: galaxies are moving away",
             "Vesto Slipher measures the light of spiral “nebulae” at Lowell Observatory and finds most shifted toward red: they are receding, some at over 1,000 km/s.",
             "File:Slipher spectrograph measured expanding universe - Flickr - brewbooks.jpg",
             "The spectrograph used at Lowell Observatory to measure how fast the spirals move."),
            ("1927", "Lemaître: speed should grow with distance",
             "Lemaître predicts from Einstein's equations that recession speed grows with distance, and calculates the rate from real data.",
             None, None),
            ("1929", "Hubble: the straight line",
             "Hubble measures distances with Cepheid variable stars and confirms it: velocity rises in step with distance. His original plot is shown here.",
             "File:Hubble's law original 1929.png",
             "Hubble's original 1929 plot."),
            ("1998", "Two teams: the expansion is speeding up",
             "Teams led by Saul Perlmutter, and by Brian Schmidt and Adam Riess, find distant Type Ia supernovae fainter than expected. The expansion is accelerating. Nobel Prize 2011.",
             "File:SN1994D.jpg",
             "Supernova 1994D, a Type Ia explosion in the galaxy NGC 4526. Exploding stars like this are “standard candles” used to measure the expansion."),
            ("2019–today", "The Hubble tension",
             "Measurements from the early universe (Planck, about 67 km/s/Mpc) and from nearby supernovae (about 73) disagree by more than expected: an open puzzle.",
             "File:Bridge diagram showing different measurements of the Hubble constant (bridge-info CORRECTED4).jpg",
             "Different measurements of the Hubble constant (NOIRLab)."),
        ],
        fit=dict(
            fits=[
                "The verb form is continuing (“We are expanding it”), matching a universe that is still expanding.",
                "The heaven is “constructed with strength” first and then expanded, matching a structured cosmos whose space stretches.",
            ],
            gaps=[
                "Some classical scholars read musiʿūn as “We have vast capacity”, not “expanders”.",
                "The verse gives no rate, no acceleration and no mechanism.",
                "In physics, space itself stretches; the word “expander” alone does not say that.",
            ],
        ),
    ),
    "orbits": dict(
        proof=[
            ("1543", "Copernicus: the Sun at the centre",
             "Nicolaus Copernicus puts the Sun near the centre and sets the planets moving around it.", None, None),
            ("1609–1619", "Kepler: ellipses, not circles",
             "Using Tycho Brahe's measurements of Mars, Johannes Kepler shows that planets follow ellipses and sweep equal areas in equal times.",
             None, None),
            ("1687", "Newton: gravity explains the paths",
             "Isaac Newton shows that one force, gravity, explains falling apples, the Moon's orbit and the tides. Every orbit is a fall that keeps missing.",
             None, None),
            ("1758", "Halley's comet returns",
             "Edmond Halley used Newton's laws to predict that a comet would return in 1758, long after his death. It did: the first proof that gravity governs comets too.",
             "File:Comet Halley.jpg",
             "Halley's Comet crossing the Milky Way, 1986 (NASA)."),
            ("1846", "Neptune found by calculation",
             "Urbain Le Verrier calculated where an unseen planet must be from oddities in Uranus's orbit. Astronomers found Neptune within about one degree of his prediction on the first night.",
             None, None),
        ],
        fit=dict(
            fits=[
                "Everything named (Sun, Moon, night and day) is described as moving along its own path.",
                "Yasbaḥūn (glide freely) suits bodies moving through space without touching anything.",
                "55:5 says the Sun and Moon move by exact calculation, matching eclipses that can be predicted years ahead.",
            ],
            gaps=[
                "Falak in classical Arabic astronomy meant a sphere or course, not specifically an ellipse.",
                "The verse does not say what keeps the bodies on their paths (gravity).",
                "Ancient astronomers also knew the Sun, Moon and planets follow regular paths; the verse describes rather than reveals a hidden law.",
            ],
        ),
    ),
    "sun-moving": dict(
        proof=[
            ("1718", "Halley: the “fixed stars” are not fixed",
             "Edmond Halley notices that Sirius, Arcturus and Aldebaran have shifted since the ancient Greeks charted them.",
             None, None),
            ("1783", "Herschel: the Sun is travelling",
             "William Herschel analyses the motions of stars and concludes that the Sun itself is moving through space, toward the constellation Hercules.",
             "File:Sir William Herschel, his life and works (1881), portrait.jpg",
             "William Herschel."),
            ("1918", "Shapley: the Sun is far from the centre",
             "Harlow Shapley maps the globular clusters of the Milky Way and shows that the Sun lies far from the galaxy's centre.",
             None, None),
            ("1927", "Oort: the galaxy rotates",
             "Jan Oort shows that the stars of the Milky Way rotate around a distant centre, with the Sun among them.",
             None, None),
            ("2020", "Gaia: the acceleration is measured",
             "ESA's Gaia satellite measures how the Solar System accelerates toward the galactic centre: about 7 mm/s more every year.",
             "File:Measuring the acceleration of the Solar System with Gaia ESA445388.webm",
             "ESA/Gaia measures the Solar System's acceleration."),
        ],
        fit=dict(
            fits=[
                "The verse says the Sun runs (tajrī), a verb of motion, when many people took the Sun as the fixed centre of the sky.",
                "21:33 says all the bodies, the Sun included, “swim” in their orbits.",
            ],
            gaps=[
                "Mustaqarr is read as a resting place, a fixed course or an appointed end.",
                "The verse does not mention the galaxy or the planets travelling with the Sun.",
                "The Sun's apparent daily movement across the sky also fits the verse.",
            ],
        ),
    ),
    "sun-moon-light": dict(
        proof=[
            ("c. 450 BCE", "Anaxagoras: the Moon borrows light",
             "The Greek philosopher Anaxagoras says the Moon shines by reflected sunlight. This was already known long before the Quran.",
             None, None),
            ("1920", "Eddington: stars burn hydrogen",
             "Arthur Eddington argues that stars shine by fusing hydrogen into helium.", None, None),
            ("1938–39", "Bethe: the Sun's reactions",
             "Hans Bethe works out the nuclear reactions that power the Sun. Nobel Prize 1967.", None, None),
            ("1968", "Davis: neutrinos from the core",
             "Ray Davis catches neutrinos from the Sun's core in a tank of cleaning fluid deep underground: direct proof that fusion is burning there. Nobel Prize 2002.",
             "File:Neutrino detector - National Museum of Nature and Science, Tokyo - DSC07824.JPG",
             "A neutrino detector exhibit: the kind of instrument that sees the Sun's core."),
        ],
        fit=dict(
            fits=[
                "Different words are used: sirāj (a burning lamp) for the Sun and nūr (light) for the Moon.",
                "10:5 calls the Sun's light ḍiyāʾ (radiance) and the Moon's nūr. Many Arabic lexicographers treat ḍiyāʾ as light that comes from a source itself.",
            ],
            gaps=[
                "Greek astronomers already knew the Moon reflects sunlight.",
                "Nūr and ḍiyāʾ are also used loosely elsewhere, so the distinction is not absolute.",
                "The verse says nothing about nuclear fusion.",
            ],
        ),
    ),
    "protected-sky": dict(
        proof=[
            ("1913", "Fabry and Buisson: the ozone layer",
             "Charles Fabry and Henri Buisson find that the Sun's ultraviolet is cut off sharply at short wavelengths: a layer of ozone high in the atmosphere is absorbing it.",
             None, None),
            ("1958", "Explorer 1: belts of trapped radiation",
             "America's first satellite carries James Van Allen's radiation counter. It is overwhelmed in some regions: belts of charged particles trapped by Earth's magnetic field.",
             "File:Rendering of Van Allen radiation belts of Earth.jpg",
             "The Van Allen belts (NASA)."),
            ("1985", "Farman and colleagues: a hole in the shield",
             "British Antarctic Survey scientists Joe Farman, Brian Gardiner and Jonathan Shanklin report a huge loss of ozone over Antarctica, showing how much the shield matters. The Montreal Protocol follows in 1987.",
             "File:2009 Antarctic Ozone Hole (3927062424).jpg",
             "The Antarctic ozone hole seen from space (NASA)."),
            ("2015", "MAVEN: a planet without a shield",
             "NASA's MAVEN orbiter measures Mars losing its atmosphere to the solar wind, showing what can happen to a planet without a strong magnetic shield.",
             "File:Charged particles from a solar storm stripping away charged particles of Mars' atmosphere, one of the processes of Martian atmosphere loss.jpg",
             "Solar storm particles stripping Mars's atmosphere (NASA/MAVEN illustration)."),
        ],
        fit=dict(
            fits=[
                "Two shields work overhead: the atmosphere absorbs harmful radiation and burns up meteoroids, and the magnetic field deflects charged particles.",
                "“Ceiling” (saqf) and “protected” (maḥfūẓ) fit a covering that guards those beneath it.",
            ],
            gaps=[
                "Samāʾ can mean the sky, the atmosphere or the heavens in general.",
                "“Protected” has also been read as protected from falling or from devils; the radiation reading is modern.",
                "Older cultures also called the sky a canopy; ozone and the magnetosphere are not in the verse.",
            ],
        ),
        sounds=[],
    ),
    "speed-of-light": dict(
        proofHeading="How the real speed of light was measured",
        proof=[
            ("1676", "Rømer: light takes time",
             "Ole Rømer times the eclipses of Jupiter's moon Io and finds them arriving late when the Earth is farther away. Light has a finite speed; he estimates about 220,000 km/s.",
             None, None),
            ("1849", "Fizeau: no astronomy needed",
             "Hippolyte Fizeau sends a light beam through a spinning toothed wheel to a mirror several kilometres away and back, and measures about 315,000 km/s on Earth.",
             None, None),
            ("1983", "The metre is defined by light",
             "The metre is redefined as the distance light travels in 1/299,792,458 of a second, so the speed of light is now exactly 299,792,458 m/s.",
             None, None),
        ],
        fit=dict(
            fits=[
                "If every choice in the author's method is made, the answer lands within 0.001% of the true value.",
                "The verses do link a “day” to “a thousand years”, so a ratio can be formed.",
            ],
            gaps=[
                "The verses never mention light, the Moon or a distance.",
                "The number needs the Earth-motion correction, which critics say is chosen to make it fit.",
                "70:4 (50,000 years) gives 50 times the speed of light with the same recipe.",
                "Classical commentators read these verses as divine time or the Day of Judgment, not physics.",
                "A mathematician's review says the “distance the Moon travels” in this recipe has no natural physical meaning.",
            ],
        ),
    ),
    "pairs": dict(
        proof=[
            ("1694", "Camerarius: plants have sexes",
             "Rudolf Camerarius removes the pollen-bearing parts of plants such as castor bean and mulberry and finds they fail to set viable seed: the first experimental proof that plants reproduce sexually.",
             None, None),
            ("1928–31", "Dirac: a mirror-image particle",
             "Paul Dirac's equation for the electron has a mirror-image solution. He predicts a particle with the electron's mass and the opposite charge.",
             None, None),
            ("1932", "Anderson: the positron is photographed",
             "Carl Anderson sees a cosmic-ray track curving the wrong way in a cloud chamber: the positron. Nobel Prize 1936.",
             "File:PositronDiscovery.jpg",
             "Anderson's 1932 cloud-chamber photograph."),
            ("1955", "The antiproton",
             "Emilio Segrè and Owen Chamberlain create the antiproton at Berkeley. Nobel Prize 1959.", None, None),
            ("1995", "Antihydrogen atoms",
             "CERN makes the first atoms of antihydrogen: a whole atom of antimatter.", None, None),
        ],
        fit=dict(
            fits=[
                "36:36 covers plants (“what the earth grows”), people (“themselves”) and things “they do not know”: a category open to later discoveries.",
                "Pairing shows up at many levels: the sexes of plants and animals, positive and negative charge, particles and antiparticles.",
            ],
            gaps=[
                "Many organisms reproduce without pairs, such as bacteria and some plants and animals.",
                "Zawj can mean “kind” or “type”, and several commentators read the verses as “every kind”.",
                "Antimatter was not something the verse's first audience could have had in mind, so that reading is a modern extension.",
            ],
        ),
    ),
    "iron-from-space": dict(
        proof=[
            ("1794", "Chladni: iron falls from space",
             "Ernst Chladni argues that masses of iron found on the ground fell from space. Scientists doubt him until stones fall at L'Aigle, France, in 1803.",
             None, None),
            ("1957", "B²FH: stars make the elements",
             "Margaret and Geoffrey Burbidge, William Fowler and Fred Hoyle show how stars forge all the heavier elements; iron sits at the peak of stellar fusion.",
             None, None),
            ("1987", "SN 1987A: iron caught being made",
             "Satellites and balloons detect gamma rays from radioactive cobalt-56, which decays into iron-56, in the exploding star SN 1987A: direct evidence that supernovae make iron.",
             "File:SN 1987A Apr 02 1999.jpg",
             "The remains of SN 1987A (Hubble)."),
            ("2016", "Tutankhamun's dagger",
             "Italian and Egyptian scientists use X-ray analysis to show that the iron blade of Tutankhamun's dagger has meteorite chemistry.",
             None, None),
        ],
        fit=dict(
            fits=[
                "Iron “sent down” (anzalnā): iron atoms do come from outside the Earth, from stars, and sometimes arrive as meteorites.",
                "The verse links iron to “great military might and benefits for people”, which fits iron's role in tools and weapons.",
            ],
            gaps=[
                "The same verb, anzala, is used for rain, cattle and clothing (39:6, 7:26), where it means “bestowed”, so the space-origin reading is not compulsory.",
                "The verse gives no sign of supernovae or nucleosynthesis.",
                "Most of the Earth's iron was already in the material the planet formed from, not sent down later.",
            ],
        ),
    ),

    # ------------------------------------------------------------ EARTH & SEAS
    "earth-shape": dict(
        proof=[
            ("c. 350 BCE", "Aristotle: the round shadow",
             "Aristotle points to the Earth's curved shadow on the Moon during eclipses, and to different stars appearing as you travel north or south.",
             None, None),
            ("c. 240 BCE", "Eratosthenes measures the Earth",
             "By comparing the Sun's angle at Alexandria and at Syene at noon on the summer solstice, Eratosthenes estimates the Earth's circumference, close to the true value.",
             "File:Eratosthenes measurement.jpg",
             "How Eratosthenes measured the Earth."),
            ("1522", "Around the world",
             "The expedition begun by Magellan, and completed by Elcano, returns to Spain after sailing around the world.",
             None, None),
            ("1736–37", "Maupertuis: flattened at the poles",
             "A French expedition to Lapland measures a degree of latitude and confirms Newton's prediction that the Earth is slightly flattened.",
             None, None),
            ("1968", "Apollo 8: the first Earthrise",
             "Astronauts photograph the whole Earth as a sphere rising over the Moon.",
             "File:AS08-13-2329.jpg",
             "The first Earthrise photographed by humans (Apollo 8)."),
        ],
        fit=dict(
            fits=[
                "Yukawwir (from kawr, winding a turban) evokes night and day coiling around a rounded body.",
                "The picture is of endless coiling, matching the continuous sweep of the day–night boundary.",
            ],
            gaps=[
                "A spherical Earth was already known to the Greeks and early Muslim scholars, so this is not new information.",
                "Kawr can also mean simply to fold or wrap, without necessarily meaning round.",
                "Daḥāhā (79:30) does not describe shape; the “ostrich-egg” reading is debated.",
            ],
        ),
    ),
    "mountains-pegs": dict(
        proof=[
            ("1738–40", "Bouguer: the mountain pulls too little",
             "During the French geodesic mission to Peru, Pierre Bouguer finds that Chimborazo deflects a plumb line far less than its bulk should. Something light must lie beneath it.",
             "File:Chimborazo 04.jpg",
             "Chimborazo, the mountain Bouguer studied."),
            ("1855", "Pratt and Airy: mountains float",
             "Surveying India, John Pratt finds the Himalaya pull plumb lines less than expected. George Airy explains it: mountains float on the mantle with deep roots, like icebergs.",
             "File:Airy isostasy.png",
             "Airy's idea: the mountain's root displaces the denser mantle."),
            ("1909", "Mohorovičić: the crust's floor",
             "Andrija Mohorovičić studies an earthquake in Croatia and finds seismic waves speed up at a sharp boundary: the bottom of the crust, now called the Moho.",
             None, None),
            ("1990s", "INDEPTH: the Tibetan root",
             "Seismic surveys across Tibet measure a crust about 70 km thick, twice normal: the root of the Himalaya.",
             None, None),
        ],
        fit=dict(
            fits=[
                "Awtād (stakes) fits mountains whose bulk is hidden below the surface, as most of a tent peg is.",
                "16:15 and 78:7 say mountains are set firmly (rawāsī), matching roots that hold the crust in balance.",
            ],
            gaps=[
                "“Lest it shift with you” is not about earthquakes; mountain belts are among the most earthquake-prone places.",
                "Awtād may simply describe firmness, as a peg does, without any reference to roots.",
                "For the first audience, mountains were heavy things fixing the ground; roots are the modern reading.",
            ],
        ),
    ),
    "water-cycle": dict(
        proof=[
            ("c. 1580", "Palissy: springs come from rain",
             "The potter and naturalist Bernard Palissy argues that springs are fed by rain soaking into the ground, against the belief that seawater seeps up through the earth.",
             None, None),
            ("1674", "Perrault: measuring it",
             "Pierre Perrault measures rainfall over the upper Seine basin and compares it with the river's flow. Rain is about six times more than needed: rain alone feeds rivers.",
             None, None),
            ("1686", "Mariotte: the river measured",
             "Edme Mariotte measures the Seine's flow at Paris and confirms Perrault.", None, None),
            ("1687", "Halley: closing the loop",
             "Edmond Halley measures how fast the Sun evaporates water and shows that the Mediterranean loses about as much as its rivers bring in.",
             None, None),
            ("2002", "GRACE: the cycle from space",
             "NASA's twin GRACE satellites weigh changes in groundwater from orbit, watching water move across whole continents.",
             "File:USGS WaterCycle English ONLINE 20221013.png",
             "The water cycle (USGS)."),
        ],
        fit=dict(
            fits=[
                "Winds move the clouds (30:48), rain falls in a set measure (23:18), and it is lodged in the ground and comes out as springs (39:21): three verses cover the loop.",
                "“In due measure” (biqadar) matches a balanced budget of evaporation and rainfall.",
            ],
            gaps=[
                "Vitruvius and others knew that springs come from rain long before Perrault.",
                "The verses give the sequence, not the mechanism (evaporation and condensation).",
                "The sequence is what anyone can observe: winds, clouds, rain, springs.",
            ],
        ),
    ),
    "hail-clouds": dict(
        proof=[
            ("1802–03", "Howard: naming the clouds",
             "Luke Howard classifies clouds as cumulus, stratus and cirrus. His names are still used.",
             None, None),
            ("1946–47", "The Thunderstorm Project",
             "The first big US field study flies aircraft and radar into thunderstorms and shows how a storm cell grows, matures and rains out.",
             None, None),
            ("1970s–80s", "Cloud merging",
             "Radar studies led by Joanne Simpson show that neighbouring cumulus clouds that merge produce more rain than when they stay apart.",
             None, None),
            ("1990s", "Doppler radar",
             "Doppler radar networks track the winds inside storms and measure updrafts above 100 km/h, which carry hail up and down until it grows too heavy.",
             "File:ISS-40 Thunderheads near Borneo.jpg",
             "Towering thunderclouds seen from the ISS."),
        ],
        fit=dict(
            fits=[
                "The sequence matches: clouds driven gently, joined, stacked, then rain from within.",
                "“Mountains” of cloud holding hail matches cumulonimbus towers.",
                "The same verse mentions lightning, and thunderstorms make both hail and lightning.",
            ],
            gaps=[
                "Some commentators read “mountains in the sky” as mountains of hail or as heavenly mountains, not cloud towers.",
                "Cloud merging is not named; the verse gives what people can see.",
                "Rain falling from within a cloud is something anyone can watch.",
            ],
        ),
    ),
    "two-seas": dict(
        proof=[
            ("1681", "Marsigli: two currents at once",
             "Luigi Marsigli explains a puzzle at the Bosphorus, where fresher water flows one way at the surface and saltier water the other way below. He demonstrates it with two liquids of different densities.",
             None, None),
            ("1870", "Carpenter and Jeffreys at Gibraltar",
             "On HMS Porcupine, William Carpenter and Gwyn Jeffreys lower a drogue to 350 m at Gibraltar and find water flowing out of the Mediterranean against the surface current.",
             None, None),
            ("1925–27", "The Meteor expedition",
             "The German research ship Meteor maps temperature and saltiness across the Atlantic and identifies a tongue of Mediterranean water spreading far out at depth.",
             None, None),
            ("2004", "Seen from the ISS",
             "Astronauts photograph internal waves at Gibraltar, on the boundary where the two waters meet.",
             "File:InternalWaves Gibraltar ISS009-E-09952 54 (detail).jpg",
             "Internal waves at the Strait of Gibraltar (ISS)."),
        ],
        fit=dict(
            fits=[
                "Two seas “meeting” with a barrier between them (barzakh) matches two water bodies meeting at Gibraltar with a density boundary.",
                "25:53 speaks of fresh water and salt water kept apart, matching river mouths and salt wedges.",
            ],
            gaps=[
                "The waters do mix slowly; the “barrier” is a gradient, not a wall.",
                "Many classical commentators took the two seas to be the fresh and the salt, or this world and the next.",
                "The viral “two oceans that don't mix” photos (Gulf of Alaska) show glacier silt and are not evidence.",
            ],
        ),
    ),
    "deep-sea-darkness": dict(
        proof=[
            ("1893", "Nansen: dead water",
             "Fridtjof Nansen's ship Fram is slowed almost to a halt in Arctic waters by “dead water”: a layer of fresh water lying over salt water.",
             "File:Fram Bergen 1893.jpg",
             "Nansen's ship Fram leaving Bergen, 1893."),
            ("1904", "Ekman: waves under the surface",
             "Vagn Walfrid Ekman shows that the ship makes waves below the surface, between the layers, that drag on it: internal waves.",
             None, None),
            ("1934", "Beebe and Barton: 923 metres down",
             "Two men descend 923 m in a steel sphere. William Beebe reports that sunlight fades to black.",
             None, None),
            ("Since the 1970s", "Internal waves from space",
             "Satellites photograph the surface signature of internal waves, and ISS crews photograph them too.",
             "File:Oceanic nonlinear internal solitary waves from the Lombok Strait (MODIS 2016-11-05).jpg",
             "Internal waves seen from space in the Lombok Strait (NASA)."),
        ],
        fit=dict(
            fits=[
                "Darkness deepens with depth, with waves above waves and clouds above: a layered scene.",
                "“Wave over wave” matches an ocean with surface waves above internal waves.",
                "A hand held out can hardly be seen, matching the darkness of the deep ocean.",
            ],
            gaps=[
                "Classical commentators pictured a stormy sea at night, which the words also fit.",
                "Internal waves cannot be seen from a ship; that reading is modern.",
                "It is a simile (“like darknesses”), not a scientific description.",
            ],
        ),
    ),
    "fertilizing-winds": dict(
        proof=[
            ("1694", "Camerarius: plants have sexes",
             "Rudolf Camerarius shows that plants need pollen to make seeds.", None, None),
            ("1761–66", "Kölreuter: pollen must travel",
             "Josef Kölreuter shows that pollen must reach the stigma, carried by insects or wind, for seeds to form.",
             "File:Pine releasing pollen into the wind in Tuntorp 1.jpg",
             "A pine releasing pollen into the wind."),
            ("1880", "Aitken: raindrops need dust",
             "John Aitken shows that cloud droplets need tiny particles to form on; in perfectly clean air, mist does not appear.",
             None, None),
        ],
        fit=dict(
            fits=[
                "Lawāqiḥ (fertilizing) fits wind carrying pollen to fertilize plants.",
                "It also fits winds that “impregnate” clouds, as many classical commentators read it.",
                "The verse connects wind and rain in one sentence.",
            ],
            gaps=[
                "Wind is only one way plants are pollinated; insects carry much of the pollen.",
                "The classical reading was about clouds; the pollen reading is one modern commentators add.",
                "The verse names neither pollen nor condensation nuclei.",
            ],
        ),
    ),

    # ----------------------------------------------------------- LIVING THINGS
    "life-from-water": dict(
        proof=[
            ("1674", "Leeuwenhoek: life in a drop",
             "Antonie van Leeuwenhoek sees living “animalcules” in a drop of lake water through his microscope: life teeming in water.",
             "File:Leeuwenhoek Microscope.png",
             "A Leeuwenhoek microscope."),
            ("1924–29", "Oparin and Haldane: life began in water",
             "Alexander Oparin and J. B. S. Haldane independently propose that life arose in a warm, watery “primordial soup”.",
             None, None),
            ("1953", "Miller and Urey: building blocks from water",
             "Stanley Miller passes sparks through water and gases thought to have been on the early Earth and makes amino acids, the building blocks of proteins.",
             "File:Miller-Urey-Experiment.png",
             "The Miller–Urey experiment."),
            ("1977", "Alvin: life around deep-sea vents",
             "The submersible Alvin finds thriving communities around hot vents on the deep sea floor, living without sunlight but not without water.",
             "File:Campagne HYDRONAUT - Vers polychètes Riftia pachyptila (Ifremer 00530-64221).jpg",
             "Giant tube worms at a hydrothermal vent (Ifremer)."),
            ("2017", "The oldest fossils",
             "Stromatolites in Western Australia, about 3.48 billion years old, are among the oldest known signs of life.",
             "File:Stromatolite (Dresser Formation, Paleoarchean, 3.48 Ga; Normay Mine, North Pole Dome, Pilbara Craton, Western Australia) 1 (32857204117).jpg",
             "A 3.48-billion-year-old stromatolite from Western Australia."),
        ],
        fit=dict(
            fits=[
                "Every known organism depends on liquid water: cells are mostly water and biochemistry happens in it.",
                "Life very probably began in water and lived there for billions of years before reaching land.",
            ],
            gaps=[
                "“Water” may also mean rain giving life to the earth, and scholars read it in several ways.",
                "“Every living thing” is broad; some organisms survive dry for years in dormant form.",
                "The verse says nothing about how life began.",
            ],
        ),
    ),
    "honey-bee": dict(
        proof=[
            ("1609", "Butler: the “king” is a queen",
             "The English beekeeper Charles Butler argues in The Feminine Monarchie that the hive's ruler is a queen.",
             None, None),
            ("1669", "Swammerdam: the proof by dissection",
             "Jan Swammerdam dissects the “king” bee and finds ovaries. The ruler of the hive is female.",
             "File:Reproductive organs of the bee. Wellcome L0000176.jpg",
             "An early engraving of the reproductive organs of the bee (Wellcome Collection)."),
            ("1792", "Huber: the workers",
             "The blind Swiss naturalist François Huber and his assistant show that the queen mates in flight and that the workers are undeveloped females.",
             None, None),
            ("1892", "Van Ketel: honey kills bacteria",
             "The Dutch scientist Bernardus van Ketel reports that honey has antibacterial properties.",
             None, None),
            ("1963", "White: the enzyme",
             "Jonathan White finds the enzyme in honey that slowly produces hydrogen peroxide, one reason honey fights germs.",
             None, None),
            ("2007", "Honey dressings approved",
             "The US FDA clears the first medical-grade honey dressings for wounds.", None, None),
        ],
        fit=dict(
            fits=[
                "The commands to the bee (ittakhidhī, kulī, fasluki) are all in the feminine, and forager bees are all female.",
                "“Houses in the mountains and the trees” matches wild hives in cliffs and hollow trees.",
                "“A drink of varying colours in which is healing” matches honey's range of colours and its medical use.",
            ],
            gaps=[
                "Naḥl (bees) is a grammatically feminine collective in Arabic, so the feminine form alone does not prove the workers' sex.",
                "The verse does not say honey cures everything; the evidence supports specific uses.",
                "The bee's “inspiration” (waḥy) is not something science addresses.",
            ],
        ),
    ),
    "milk-from-blood": dict(
        proof=[
            ("1960s–70s", "Linzell: following the blood",
             "John Linzell measures blood flow through the mammary glands of lactating goats, and what the blood loses on the way through. Hundreds of times more blood passes through than the milk that is made.",
             "File:Cow female black white.jpg",
             "A dairy cow (USDA)."),
            ("Since then", "Dairy science confirms it",
             "Sugars, amino acids and fats absorbed from the gut are carried by the blood to the udder, where the gland cells remove them and assemble milk.",
             None, None),
        ],
        fit=dict(
            fits=[
                "Nutrients absorbed from the gut go into the blood, and the mammary gland builds milk from the blood: “between” the two.",
                "“Pure milk, pleasant to drink” contrasts the milk with what it comes from.",
            ],
            gaps=[
                "Farth means partly digested matter in the gut; the physiology reading is modern.",
                "Milk is not made in the gut or directly between gut and blood; it is made in the gland from blood.",
                "Older commentators read the verse as a lesson in God's provision, without physiology.",
            ],
        ),
    ),

    # --------------------------------------------------------- HUMAN CREATION
    "embryo-stages": dict(
        proof=[
            ("1651", "Harvey: watching development",
             "William Harvey opens hens' eggs at intervals, and studies deer embryos, and describes development stage by stage: omne vivum ex ovo, every animal from an egg.",
             "File:Harvey. Animalium. Ex ovo omnium.png",
             "The frontispiece of Harvey's book: everything from an egg."),
            ("1672–75", "Malpighi: the chick under the microscope",
             "Marcello Malpighi watches the chick embryo through a microscope, seeing the heart beat and the body form.",
             None, None),
            ("1827", "Von Baer: the mammalian egg",
             "Karl Ernst von Baer discovers the egg cell of mammals in a dog's ovary.", None, None),
            ("1875", "Hertwig: fertilisation seen",
             "Oscar Hertwig watches a sperm enter a sea-urchin egg and the two nuclei fuse.", None, None),
            ("1942–87", "The Carnegie stages",
             "George Streeter and later Ronan O'Rahilly sort a museum collection of human embryos into 23 stages, giving the standard timetable of development.",
             "File:Contributions to embryology (20696681371).jpg",
             "A page from the Carnegie Institution's Contributions to Embryology."),
            ("1978", "IVF: watching the first days",
             "The first IVF baby lets doctors watch human embryos from fertilisation to implantation for the first time.",
             "File:Human Embryo - Approximately 8 weeks estimated gestational age.jpg",
             "A human embryo at about 8 weeks (museum specimen)."),
        ],
        fit=dict(
            fits=[
                "The order drop → clinging thing → chewed-like lump → bones → flesh → “another creation” matches the order blastocyst implants, embryo with somites, skeleton and muscle, recognisably human form.",
                "The words are visual and concrete, matching stages a physician could see.",
            ],
            gaps=[
                "Cartilage and muscle form together, so “bones then flesh” is not a clean sequence.",
                "Galen and Aristotle already described stages of the embryo, so the words may describe what earlier physicians knew.",
                "ʿAlaqah has been read as “blood clot”, “leech-like” or “clinging thing”; the last is the modern reading.",
                "The resemblance of somites to a chewed morsel depends on the model used.",
            ],
        ),
    ),
    "sex-determination": dict(
        proof=[
            ("1891–1902", "The X chromosome appears",
             "Hermann Henking sees a strange chromosome in insects; in 1902 Clarence McClung suggests it may decide sex.", None, None),
            ("1905", "Stevens and Wilson: sex chromosomes",
             "Nettie Stevens, studying mealworms, and Edmund Wilson, studying bugs, each show that males and females differ by one chromosome, and that sex depends on which sperm meets the egg.",
             "File:Nettie Maria Stevens (cropped).jpg",
             "Nettie Stevens."),
            ("1959", "XXY and XO: the Y decides",
             "People with XXY chromosomes are male (Klinefelter syndrome), and those with a single X are female (Turner syndrome): it is the Y that makes the difference.",
             None, None),
            ("1990–91", "SRY: the switch",
             "Peter Goodfellow's team finds the SRY gene on the Y chromosome; in 1991 Koopman's team shows that adding it to female mice makes them male.",
             "File:Human karyotype diagram showing autosomes and sex chromosomes - NHGRI.jpg",
             "The human chromosomes; the sex chromosomes are the last pair."),
        ],
        fit=dict(
            fits=[
                "The verse says both sexes come “from a sperm-drop when it is emitted”, and the sperm's X or Y sets the sex.",
                "75:37–39 repeats it: an emitted drop, a clinging thing, then male and female.",
            ],
            gaps=[
                "Nuṭfah can mean the mixed fluid, not specifically the chromosomes.",
                "Classical commentators read the verse as God's power over creation, not chromosome biology.",
                "The egg supplies half the chromosomes; the sperm does not create the child alone.",
            ],
        ),
    ),
    "senses-order": dict(
        proof=[
            ("1980", "DeCasper and Fifer: newborns know the voice",
             "Newborns suck faster to hear their mother's voice than another woman's: they learned it in the womb.", None, None),
            ("1994", "Hepper and Shahidullah: sound at 19 weeks",
             "Ultrasound shows fetuses moving in response to sound from 19 weeks of pregnancy.", None, None),
            ("Textbook embryology", "The eyes open later",
             "The eyelids fuse shut around week 10 and reopen around week 26, and the retina responds to light later still.",
             None, None),
        ],
        fit=dict(
            fits=[
                "Hearing is listed before sight in 32:9, 76:2 and other verses.",
                "Fetal hearing works weeks before the eyes open.",
            ],
            gaps=[
                "The Arabic wa (“and”) does not set an order.",
                "Hearing before sight may simply be the usual order in which the Quran lists them.",
                "“Hearts”, listed third in 32:9, are the first organ to work in the embryo, so the list is not a developmental timeline.",
            ],
        ),
    ),
    "fingerprints": dict(
        proof=[
            ("1684", "Grew: the ridges",
             "Nehemiah Grew describes the ridges and pores on fingers and palms.", None, None),
            ("1788", "Mayer: no two alike",
             "Johann Mayer states that no two people's fingerprint patterns are exactly alike.", None, None),
            ("1823", "Purkinje: nine patterns",
             "Jan Purkinje sorts fingerprints into nine pattern types.",
             "File:9 fingerprints by Purkinje.png",
             "Purkinje's nine fingerprint types."),
            ("1892", "Galton: permanent and unique",
             "Francis Galton shows that fingerprints stay the same through life and are distinctive enough to identify people. The same year, Juan Vucetich in Argentina uses a bloody fingerprint to solve a murder.",
             "File:Fingerprint - Plain Whorl.jpg",
             "A fingerprint: a plain whorl."),
            ("2002", "Twins",
             "Studies of identical twins find their fingerprints are similar but different.", None, None),
        ],
        fit=dict(
            fits=[
                "Banān (fingertips) names the part of the body whose ridges identify each person.",
                "The verse stresses God's power to shape even the finest detail.",
            ],
            gaps=[
                "The context is resurrection, not identification.",
                "Banān can mean fingers or fingertips generally.",
                "Fingerprints were pressed into clay as marks in ancient Babylon and China, so their individuality was not hidden.",
                "Science has not proved that no two prints are identical, only that none has been found.",
            ],
        ),
    ),

    # ------------------------------------------------------- SIGNS & HISTORY
    "moon-split": dict(
        proofHeading="What can be checked",
        proof=[
            ("c. 620 CE", "The reports",
             "Several companions, including Abdullah ibn Masʿud and Anas ibn Malik, narrate that the Moon appeared split in two at Makkah.",
             None, None),
            ("9th century", "The collections",
             "Al-Bukhari (d. 870) and Muslim (d. 875) record the reports in their Sahih collections, which Muslim scholars grade as authentic.",
             None, None),
            ("Today", "What the Moon's channels really are",
             "Apollo and Lunar Reconnaissance Orbiter photographs show that the Moon's long channels (rilles) are old lava channels and fault valleys. They are not a split and are unrelated to this event.",
             "File:Apollo 15 - Hadley Rille from orbit.jpg",
             "Hadley Rille, a lunar lava channel (Apollo 15)."),
        ],
        fit=dict(
            fits=[
                "Verse 54:1 states the event in the past tense right after “the Hour has drawn near”.",
                "54:2 says that when they see a sign they turn away and call it magic. Classical commentaries connect this to the Makkans calling it sorcery.",
            ],
            gaps=[
                "No physical test is possible: it is a reported miracle, accepted on the strength of transmission.",
                "A minority of scholars read inshaqqa as a future sign of the Last Hour.",
                "Historians do not generally accept any independent record outside the Muslim sources.",
                "Claims that NASA photographed the split are false.",
            ],
        ),
    ),
    "roman-prophecy": dict(
        proofHeading="What the chronicles record",
        proof=[
            ("614", "Jerusalem falls",
             "Persian armies capture Jerusalem. The Armenian historian Sebeos and the Greek Antiochus Strategos record the massacre and the loss of the True Cross.",
             None, None),
            ("619", "Egypt is lost",
             "The Persians finish conquering Egypt, the granary of the Byzantine Empire.", None, None),
            ("622–627", "Heraclius strikes back",
             "Emperor Heraclius counter-attacks through Anatolia and Armenia, as the chroniclers Theophanes and the Paschal Chronicle record. In December 627 he wins at Nineveh.",
             "File:Sasanian Empire 621 A.D.jpg",
             "The Sasanian Empire at its height, about 621 CE."),
            ("628–630", "Peace and the Cross",
             "The Persian king Khosrow II is overthrown and peace is made. Heraclius returns the True Cross to Jerusalem in 630.",
             None, None),
        ],
        fit=dict(
            fits=[
                "The Romans lose badly and then win: the war reversed within about 14 years of the fall of Jerusalem.",
                "The verse places the defeat “in the nearest land”, and the Dead Sea region saw fighting.",
                "Biḍʿ means 3 to 9 years, and the Byzantine recovery began within that window (622–625).",
            ],
            gaps=[
                "The date of revelation and the start of the count of years are debated.",
                "The final victory (627–628) came 13–14 years after 614, longer than nine years; the defence counts from the start of the recovery.",
                "The story of the revelation comes from Islamic sources, although the war itself is history.",
                "Adnā al-arḍ has two readings: “nearest” or “lowest” land.",
            ],
        ),
    ),
}


from facts_evidence_more import MORE_EVIDENCE  # noqa: E402
from facts_evidence_third import THIRD_EVIDENCE  # noqa: E402

EVIDENCE.update(MORE_EVIDENCE)
EVIDENCE.update(THIRD_EVIDENCE)
