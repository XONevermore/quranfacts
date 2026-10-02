# -*- coding: utf-8 -*-
"""
Curated content for Quran Facts.

This file holds ONLY what a human writes: which verses, the science explanation,
the caveats, and which Wikimedia Commons files to show. `build_content.py` then
fetches the Quran text and resolves + verifies all media, and writes
app/src/main/assets/facts.json.

Editorial rules (please keep):
  * Every fact is tagged with a claimType so readers can see how strong the
    link is:  parallel | interpretive | historical | miracle
  * Say what the verse says first. Say what science found second. Never put
    words in the verse's mouth (e.g. "planets follow the Sun" is science, not
    something the verse states).
  * Where scholars read the verse differently, or the claim is commonly overstated online,
    say so under `fit.gaps` in facts_evidence.py (every fact needs both `fits` and `gaps`).
  * Media must be openly licensed / public domain (Wikimedia Commons).
"""

CATEGORIES = [
    dict(id="cosmos", name="The Cosmos", tagline="From the first moment to the moving Sun", accent="#6C8CFF"),
    dict(id="earth", name="Earth & Seas", tagline="Mountains, rain and the meeting of two seas", accent="#2FC4B2"),
    dict(id="insects", name="Insects & Small Creatures", tagline="The bee, the ant, the locust, the fly and the spider", accent="#FF9F43"),
    dict(id="life", name="Living Things", tagline="Water, milk, the camel, the crow and the green tree", accent="#7BD46A"),
    dict(id="human", name="Human Creation", tagline="The womb, the drop, the fingertip and the breath", accent="#FF8FA3"),
    dict(id="signs", name="Signs & History", tagline="Prophecies, ancient peoples and the oldest manuscripts", accent="#E7B65C"),
]

CLAIM_TYPES = [
    dict(
        id="parallel",
        label="Scientific parallel",
        short="Parallel",
        description="A verse describes something specific about nature that modern science has since measured or explained.",
    ),
    dict(
        id="interpretive",
        label="Interpretive reading",
        short="Interpretive",
        description="The link depends on how a word or verse is read. Respected scholars and scientists disagree, so you decide.",
    ),
    dict(
        id="historical",
        label="Historical sign",
        short="Historical",
        description="A statement about human history that can be checked against records.",
    ),
    dict(
        id="miracle",
        label="Miracle (faith)",
        short="Miracle",
        description="A reported miraculous event. Science can neither prove nor disprove it; it is included as part of the Islamic tradition, not as scientific evidence.",
    ),
]

# --------------------------------------------------------------------------
# Facts
# media entries: (commons file name, caption). First image = hero.
# words: (arabic token exactly as in the verse, transliteration, meaning, root)
# --------------------------------------------------------------------------

FACTS = [
    # ======================= COSMOS =======================
    dict(
        id="big-bang",
        title="The Big Bang",
        hook="The heavens and the earth were “a joined entity” — and then they were split apart.",
        category="cosmos",
        claimType="parallel",
        verses=["21:30", "41:11"],
        words=[
            ("رَتْقًا", "ratq", "joined together, sealed as one mass", "ر ت ق"),
            ("فَفَتَقْنَٰهُمَا", "fa-fataqnāhumā", "so We split them apart", "ف ت ق"),
            ("دُخَانٌ", "dukhān", "smoke; a hot gas", "د خ ن"),
        ],
        science=[
            "Every distant galaxy is moving away from us, and the farther away it is, the faster it recedes. Run that film backwards and all matter, energy and space converge into a single hot, dense state. That is the Big Bang model: not an explosion in space, but the expansion of space itself, starting about 13.8 billion years ago.",
            "The evidence comes in layers. Galaxies recede (Hubble, 1929). A faint glow of microwaves fills the whole sky at 2.725 K, the afterglow of the early universe, found by accident in 1965 by Penzias and Wilson. And the mix of light elements in old gas clouds, about 75% hydrogen and 25% helium by mass, matches what the model predicts.",
            "In its youth the universe was an opaque, hot gas, a kind of cosmic “smoke”, that cooled and clumped into the first stars and galaxies. Verse 41:11 speaks of the heaven at a stage when it was “smoke” (dukhān).",
        ],
        stats=[
            ("13.8 bn yrs", "Age of the universe (Planck 2018)"),
            ("2.725 K", "Temperature of the cosmic microwave background"),
            ("75% / 25%", "Hydrogen / helium in the primordial gas"),
        ],
        discovery=("1927 – 1965", "Lemaître · Hubble · Penzias & Wilson"),
        media=[
            ("File:CMB Timeline300 no WMAP.jpg", "Timeline of the universe, from the Big Bang to today (NASA/WMAP)."),
            ("File:Cosmic Microwave Background (CMB).jpeg", "The afterglow of the early universe, mapped by ESA's Planck satellite."),
            ("File:Hubble ultra deep field.jpg", "Hubble Ultra Deep Field: thousands of galaxies in a patch of sky the size of a grain of sand."),
            ("File:Cosmic Background Radiation, Redshift and the Expanding Universe..webm", "How the early universe's glow became the cosmic microwave background."),
        ],
        sources=[
            ("Big Bang", "https://en.wikipedia.org/wiki/Big_Bang"),
            ("Cosmic microwave background", "https://en.wikipedia.org/wiki/Cosmic_microwave_background"),
            ("Big Bang nucleosynthesis", "https://en.wikipedia.org/wiki/Big_Bang_nucleosynthesis"),
        ],
    ),
    dict(
        id="expanding-universe",
        title="The Expanding Universe",
        hook="A verse about a heaven that is being expanded, 1,300 years before Hubble measured it.",
        category="cosmos",
        claimType="parallel",
        verses=["51:47"],
        words=[
            ("لَمُوسِعُونَ", "la-mūsiʿūn", "“indeed We are expanders” / “We have vast capacity”", "و س ع"),
        ],
        science=[
            "In 1929 Edwin Hubble showed that distant galaxies are receding, and the farther away, the faster they go. Georges Lemaître had predicted the same relation two years earlier from Einstein's equations. The cause is not galaxies flying through space: space itself is stretching.",
            "Today's rate is about 70 km/s for every megaparsec (3.26 million light-years) of distance. Different measurement methods disagree slightly, an open puzzle called the “Hubble tension”. In 1998 two teams discovered that the expansion is even accelerating, work that earned the 2011 Nobel Prize in Physics.",
        ],
        stats=[
            ("1929", "Hubble's measurement of galaxy recession"),
            ("≈70 km/s/Mpc", "The Hubble constant"),
            ("1998", "Accelerating expansion discovered"),
        ],
        discovery=("1929", "Edwin Hubble (Lemaître, 1927)"),
        media=[
            ("File:Hubble Extreme Deep Field (full resolution).png", "Hubble eXtreme Deep Field: galaxies that are still moving apart."),
            ("File:CMB universe expansion.png", "Artist's timeline of the expanding universe (NASA/WMAP)."),
            ("File:Hubble Science- Hubble Constant, An Expanding Universe (SVS14243 CONSTANT WIDE MP4).webm", "NASA explains the Hubble constant and the expanding universe."),
        ],
        sources=[
            ("Hubble's law", "https://en.wikipedia.org/wiki/Hubble%27s_law"),
            ("Accelerating expansion of the universe", "https://en.wikipedia.org/wiki/Accelerating_expansion_of_the_universe"),
            ("Georges Lemaître", "https://en.wikipedia.org/wiki/Georges_Lema%C3%AEtre"),
        ],
    ),
    dict(
        id="orbits",
        title="Everything Moves in an Orbit",
        hook="Sun, Moon, night and day: “each in an orbit, swimming.”",
        category="cosmos",
        claimType="parallel",
        verses=["21:33", "36:40", "55:5"],
        words=[
            ("فَلَكٍ", "falak", "an orbit, a path or course", "ف ل ك"),
            ("يَسْبَحُونَ", "yasbaḥūn", "they glide / swim freely", "س ب ح"),
            ("بِحُسْبَانٍ", "bi-ḥusbān", "by precise reckoning", "ح س ب"),
        ],
        science=[
            "The Moon circles the Earth every 27.3 days at about 1 km/s. The Earth circles the Sun at about 30 km/s. The Sun itself circles the centre of the Milky Way. Nothing in the sky is nailed in place: every body follows a path set by gravity.",
            "The verb yasbaḥūn is the one used for swimming: a body gliding freely along its own path, touching nothing. Verse 36:40 adds that the Sun cannot overtake the Moon, nor night outrun day: a picture of orderly cycles that never collide.",
            "Kepler showed in 1609 that planetary orbits are ellipses, and Newton explained them with gravity in 1687. Verse 55:5 says the Sun and Moon move by “ḥusbān”, exact calculation, which is why eclipses can be predicted years in advance to within a few seconds.",
        ],
        stats=[
            ("27.3 days", "Moon's orbit around the Earth"),
            ("≈30 km/s", "Earth's speed around the Sun"),
            ("≈1 km/s", "Moon's speed around the Earth"),
        ],
        discovery=("1609 – 1687", "Kepler · Newton"),
        media=[
            ("File:Solar System Illustration (rubin-20220314-Solar-System-hero).jpg", "An illustration of the Solar System's bodies, each on its own orbit."),
            ("File:Moon Essentials- Orbit (SVS5326 - moon orbit 1080p30).webm", "NASA: the Moon's orbit around the Earth."),
            ("File:Animation of the outer Solar System and orbits of Centaurs (eso1410g).webm", "ESO animation: bodies of the outer Solar System on their orbits."),
        ],
        sources=[
            ("Orbit", "https://en.wikipedia.org/wiki/Orbit"),
            ("Kepler's laws of planetary motion", "https://en.wikipedia.org/wiki/Kepler%27s_laws_of_planetary_motion"),
            ("Solar eclipse prediction", "https://en.wikipedia.org/wiki/Solar_eclipse"),
        ],
    ),
    dict(
        id="sun-moving",
        title="The Sun Is Moving",
        hook="The Sun is not standing still. It runs a course of its own, and its planets travel with it.",
        category="cosmos",
        claimType="interpretive",
        verses=["36:38", "21:33"],
        words=[
            ("تَجْرِى", "tajrī", "it runs / flows along", "ج ر ي"),
            ("لِمُسْتَقَرٍّ", "li-mustaqarrin", "to a stopping point / fixed place / appointed course", "ق ر ر"),
        ],
        science=[
            "For most of history the Sun was thought to be the fixed centre of the sky. In 1783 William Herschel showed from the motions of stars that the Sun itself is travelling through space. In the 1920s Bertil Lindblad and Jan Oort proved that the Milky Way rotates and that the Sun circles its centre.",
            "Today we know the Sun moves at roughly 230 km/s (over 800,000 km/h) around the galactic centre, 26,000 light-years away, completing one lap about every 230 million years. In 2021 ESA's Gaia satellite even measured the tiny acceleration of the Solar System toward the centre: about 7 mm/s more every year.",
            "The planets go along for the ride. They are bound to the Sun by gravity, so the whole Solar System travels as one, like passengers in a moving train. The verse itself speaks only of the Sun's run; that the planets travel with it is established science, not a phrase we put in the verse's mouth.",
        ],
        stats=[
            ("≈230 km/s", "Sun's speed around the Milky Way"),
            ("≈230 M yrs", "One lap of the galaxy (a “galactic year”)"),
            ("26,000 ly", "Distance to the galactic centre"),
        ],
        discovery=("1783 – 1927", "Herschel · Lindblad · Oort"),
        media=[
            ("File:Center of the Milky Way Galaxy IV – Composite.jpg", "The centre of the Milky Way, which the Sun orbits (NASA/ESA/CXC)."),
            ("File:Guisard - Milky Way.jpg", "Our galaxy, the Milky Way, over the Paranal Observatory (ESO)."),
            ("File:Orbits of the nearby stars around the galaxy ESA445389.webm", "ESA/Gaia: nearby stars, the Sun among them, orbiting the galaxy."),
            ("File:Measuring the acceleration of the Solar System with Gaia ESA445388.webm", "ESA/Gaia measures the Solar System's acceleration around the galaxy."),
        ],
        sources=[
            ("Galactic year", "https://en.wikipedia.org/wiki/Galactic_year"),
            ("Solar apex", "https://en.wikipedia.org/wiki/Solar_apex"),
            ("Milky Way", "https://en.wikipedia.org/wiki/Milky_Way"),
            ("ESA: Measuring the acceleration of the Solar System with Gaia", "https://sci.esa.int/web/gaia/-/measuring-the-acceleration-of-the-solar-system-with-gaia"),
        ],
    ),
    dict(
        id="sun-moon-light",
        title="A Burning Lamp and a Borrowed Light",
        hook="The Sun is a “lamp” that burns. The Moon is a “light” that shines by borrowed brilliance.",
        category="cosmos",
        claimType="parallel",
        verses=["10:5", "71:16", "25:61"],
        words=[
            ("ضِيَآءً", "ḍiyāʾan", "radiant, self-generated brilliance", "ض و ء"),
            ("نُورًا", "nūran", "light (that may be reflected)", "ن و ر"),
            ("سِرَاجًا", "sirājan", "a burning lamp", "س ر ج"),
        ],
        science=[
            "The Sun makes its own light. In its core, at about 15 million °C, roughly 600 million tonnes of hydrogen fuse into helium every second. Hans Bethe worked out the fusion cycles that power stars in 1938–39. The Sun literally burns: a nuclear lamp.",
            "The Moon makes none. Its surface is dark rock that reflects only about 12% of the sunlight falling on it. Every glimmer of moonlight is the Sun's, borrowed.",
            "The Quran uses different words for the two: sirāj (a burning lamp) and ḍiyāʾ (radiance) for the Sun, nūr and munīr for the Moon. Whether the distinction is deliberate is a matter of linguistic interpretation, but it is precise.",
        ],
        stats=[
            ("600 M tonnes/s", "Hydrogen fused in the Sun's core"),
            ("≈15 M °C", "Temperature of the Sun's core"),
            ("≈12%", "Sunlight the Moon reflects"),
        ],
        discovery=("1938 – 39", "Hans Bethe (stellar fusion)"),
        media=[
            ("File:SDO 20200226 001038 4096 HMII (HMI).jpg", "The Sun, imaged by NASA's Solar Dynamics Observatory."),
            ("File:Full Moon Luc Viatour.jpg", "The Full Moon, lit entirely by the Sun."),
            ("File:SDO Sun This Week (SVS5577 - 048p30).webm", "NASA's Solar Dynamics Observatory: a week of the Sun."),
        ],
        sources=[
            ("Nuclear fusion in the Sun", "https://en.wikipedia.org/wiki/Solar_core"),
            ("Moon: albedo and brightness", "https://en.wikipedia.org/wiki/Albedo"),
            ("Hans Bethe", "https://en.wikipedia.org/wiki/Hans_Bethe"),
        ],
    ),
    dict(
        id="protected-sky",
        title="The Protected Ceiling",
        hook="“We made the sky a protected ceiling”, and a thin layer of gas and magnetism really does guard life.",
        category="cosmos",
        claimType="parallel",
        verses=["21:32"],
        words=[
            ("سَقْفًا", "saqfan", "a ceiling, a roof", "س ق ف"),
            ("مَّحْفُوظًا", "maḥfūẓan", "guarded, protected", "ح ف ظ"),
        ],
        science=[
            "Earth's atmosphere is a shield. The ozone layer absorbs 97–99% of the Sun's harmful ultraviolet light. The air burns up the small rocks that hit it at 11 to 72 km/s, tens of tonnes of space debris every day, so almost none reaches the ground.",
            "Higher up, Earth's magnetic field (the magnetosphere) deflects the charged particles of the solar wind. Where a few slip in near the poles, they glow as the aurora.",
            "Without this double shield the surface would be sterilised by radiation. Mars, with almost no global magnetic field and a thin atmosphere, is the cold demonstration: it lost most of its early air.",
        ],
        stats=[
            ("97–99%", "Harmful UV absorbed by the ozone layer"),
            ("11–72 km/s", "Speed of meteoroids meeting the atmosphere"),
        ],
        discovery=("1913 – 1958", "Fabry & Buisson (ozone) · Van Allen (radiation belts)"),
        media=[
            ("File:Image of aurora at night from the ISS 01.jpg", "An aurora seen from the International Space Station."),
            ("File:ISS047-E-138450 - View of Earth.jpg", "The thin blue line of the atmosphere seen from orbit."),
            ("File:Solar Wind Animations (SVS14892 CMEstrikesEarth 1080).webm", "NASA: a solar eruption strikes Earth's magnetic shield."),
        ],
        sources=[
            ("Ozone layer", "https://en.wikipedia.org/wiki/Ozone_layer"),
            ("Magnetosphere", "https://en.wikipedia.org/wiki/Magnetosphere"),
            ("Meteoroid", "https://en.wikipedia.org/wiki/Meteoroid"),
        ],
    ),
    dict(
        id="speed-of-light",
        title="The Speed of Light Puzzle",
        hook="Can the speed of light be read out of a verse? Here is the calculation, and where it becomes contested.",
        category="cosmos",
        claimType="interpretive",
        widget="lightspeed",
        scienceHeading="The calculation",
        verses=["32:5", "70:4", "22:47"],
        words=[
            ("يَعْرُجُ", "yaʿruju", "ascends", "ع ر ج"),
            ("مِقْدَارُهُۥٓ", "miqdāruhū", "its measure / extent", "ق د ر"),
        ],
        science=[
            "Verse 32:5 says that every matter “will ascend to Him in a Day, the extent of which is a thousand years of those which you count”. Verse 70:4 says the angels ascend in a Day “the extent of which is fifty thousand years”, and 22:47 says a day with your Lord is like a thousand years of your counting. Classical commentators read these as divine time, or as the Day of Judgment.",
            "A modern proposal by the Egyptian astronomer Mansour Hassab-Elnaby reads 32:5 as a distance–time statement: something covers in one day the distance the Moon covers in 12,000 lunar months (1,000 lunar years × 12). Try the arithmetic in the lab below.",
            "For reference, the speed of light is exactly 299,792.458 km/s, fixed by definition of the metre since 1983.",
        ],
        stats=[
            ("299,792.458 km/s", "The speed of light"),
            ("12,000", "Lunar months in 1,000 lunar years"),
            ("+12%", "Raw result vs. the true speed of light"),
        ],
        media=[
            ("File:Earth, Moon and Lunar Module, AS11-44-6643.jpg", "The Earth and the Moon, photographed by the Apollo 11 astronauts (NASA)."),
            ("File:Earth and Moon Visualization.ogv", "NASA: the Moon orbiting the Earth, to scale."),
            ("File:Moon Essentials- Orbit (SVS5326 - moon orbit 1080p30).webm", "NASA: the Moon's orbit over 8.5 years."),
        ],
        sources=[
            ("Speed of light", "https://en.wikipedia.org/wiki/Speed_of_light"),
            ("Sidereal time and the sidereal day", "https://en.wikipedia.org/wiki/Sidereal_time"),
            ("Lunar month", "https://en.wikipedia.org/wiki/Lunar_month"),
            ("Hassab-Elnaby's method (proponent's paper)", "http://www.islamawareness.net/Islam/speed.html"),
            ("A mathematician's review of the method", "https://answering-islam.org/Science/c_in_quran.html"),
        ],
    ),
    dict(
        id="pairs",
        title="Everything in Pairs",
        hook="“We created pairs of all things”, even things “they do not know”.",
        category="cosmos",
        claimType="interpretive",
        verses=["36:36", "51:49", "13:3"],
        words=[
            ("زَوْجَيْنِ", "zawjayn", "two mates, a pair (also: two kinds)", "ز و ج"),
            ("ٱلْأَزْوَٰجَ", "al-azwāj", "the pairs / kinds", "ز و ج"),
        ],
        science=[
            "Plants: until Rudolf Camerarius demonstrated it in 1694, plant sexes were poorly understood. Verse 13:3 says fruits were made in pairs (zawjayn). Animals: most reproduce through male and female.",
            "Physics: electric charge comes as positive and negative, and every elementary particle has an antiparticle. Paul Dirac's equation (1928–31) predicted the anti-electron, and Carl Anderson photographed it in 1932. Matter and antimatter are created in pairs.",
            "Verse 36:36 says the pairs are “from what the earth grows, from themselves, and from what they do not know”, a category wide enough to include discoveries not yet made.",
        ],
        stats=[
            ("1694", "Sexual reproduction in plants demonstrated"),
            ("1932", "Antimatter (the positron) photographed"),
        ],
        discovery=("1694 – 1932", "Camerarius · Dirac · Anderson"),
        media=[
            ("File:PositronDiscovery.jpg", "Carl Anderson's 1932 cloud-chamber photograph: the first positron track."),
            ("File:Honey Bee Collecting Pollen.jpg", "A bee carrying pollen between flowers: plants have two sexes too."),
            ("File:Pollination video.webm", "Pollination in action."),
        ],
        sources=[
            ("Positron", "https://en.wikipedia.org/wiki/Positron"),
            ("Rudolf Jakob Camerarius", "https://en.wikipedia.org/wiki/Rudolf_Jakob_Camerarius"),
            ("Antimatter", "https://en.wikipedia.org/wiki/Antimatter"),
        ],
    ),
    dict(
        id="iron-from-space",
        title="Iron “Sent Down”",
        hook="“We sent down iron”, and the iron in your blood was forged inside a dying star.",
        category="cosmos",
        claimType="interpretive",
        verses=["57:25"],
        words=[
            ("أَنزَلْنَا", "anzalnā", "We sent down / We bestowed", "ن ز ل"),
            ("ٱلْحَدِيدَ", "al-ḥadīd", "iron", "ح د د"),
        ],
        science=[
            "Iron is the end of the line for a massive star's fusion. It is forged in the final days of stars far bigger than the Sun and scattered by supernova explosions. Every atom of iron on Earth, in the core, in the ore, in the haemoglobin in your blood, arrived as stardust in the cloud that formed the Solar System. Iron makes up about 32% of Earth's mass.",
            "Iron also literally falls from the sky. Iron–nickel meteorites like Gibeon (Namibia) were humanity's first source of iron: in 2016 scientists showed that the blade of Tutankhamun's dagger was made of meteoritic iron.",
            "In 1957 Margaret and Geoffrey Burbidge, William Fowler and Fred Hoyle published the landmark paper explaining how the elements are made in stars.",
        ],
        stats=[
            ("≈32%", "Of Earth's mass is iron"),
            ("1957", "Origin of the elements in stars mapped (B²FH)"),
            ("2016", "Tutankhamun's dagger shown to be meteoritic iron"),
        ],
        discovery=("1957", "Burbidge, Burbidge, Fowler & Hoyle"),
        media=[
            ("File:Crab Nebula.jpg", "The Crab Nebula: the remains of a supernova that scattered heavy elements, including iron."),
            ("File:Gibeon meteorite, pattern.jpg", "A slice of the Gibeon iron meteorite showing its crystal pattern."),
            ("File:Hubble Captures 3 Faces of Evolving Supernova (SVS14239 SUPERNOVA WIDE MP4).webm", "Hubble watches an exploding star that forges iron."),
        ],
        sources=[
            ("Supernova nucleosynthesis", "https://en.wikipedia.org/wiki/Supernova_nucleosynthesis"),
            ("Synthesis of the Elements in Stars (B²FH)", "https://en.wikipedia.org/wiki/Synthesis_of_the_Elements_in_Stars"),
            ("Tutankhamun's meteoritic iron dagger", "https://en.wikipedia.org/wiki/Tutankhamun%27s_meteoric_iron_dagger"),
        ],
    ),

    # ======================= EARTH & SEAS =======================
    dict(
        id="earth-shape",
        title="Night Wrapped Around Day",
        hook="“He wraps the night over the day”, the language of winding a turban around a round head.",
        category="earth",
        claimType="interpretive",
        verses=["39:5", "79:30"],
        words=[
            ("يُكَوِّرُ", "yukawwir", "wraps / coils (as a turban is wound)", "ك و ر"),
            ("دَحَىٰهَا", "daḥāhā", "He spread it out / extended it", "د ح و"),
        ],
        science=[
            "The verb yukawwir comes from kawr, the winding of a turban around the head. The picture is of night and day coiling around each other without end, which is exactly what happens on a rotating sphere: the line between day and night (the terminator) sweeps around the globe once every 24 hours.",
            "The Earth is an oblate spheroid: round, and slightly flattened. Its equatorial diameter is about 43 km longer than pole to pole (12,756 km versus 12,714 km).",
            "Verse 79:30 says the Earth was “spread out” for habitation, a phrase about the ground being made a place to live rather than about its overall shape.",
        ],
        stats=[
            ("24 h", "One sweep of the day–night line around the globe"),
            ("12,756 km", "Equatorial diameter"),
            ("12,714 km", "Pole-to-pole diameter"),
        ],
        media=[
            ("File:Blue Marble Eastern Hemisphere.jpg", "The Earth as a sphere (NASA Blue Marble)."),
            ("File:Earthterminator iss002 full.jpg", "The day–night boundary sweeping across the Earth, seen from the ISS."),
            ("File:DSCOVR EPIC Earth Rotation.webm", "A day of the Earth's rotation, filmed from a million miles away by NASA's DSCOVR."),
        ],
        sources=[
            ("Spherical Earth", "https://en.wikipedia.org/wiki/Spherical_Earth"),
            ("Terminator (solar)", "https://en.wikipedia.org/wiki/Terminator_(solar)"),
            ("Figure of the Earth", "https://en.wikipedia.org/wiki/Figure_of_the_Earth"),
        ],
    ),
    dict(
        id="mountains-pegs",
        title="Mountains as Pegs",
        hook="Mountains are called “stakes”, because most of a mountain is hidden below the ground.",
        category="earth",
        claimType="parallel",
        verses=["78:6", "78:7", "16:15"],
        words=[
            ("أَوْتَادًا", "awtād", "stakes, tent pegs", "و ت د"),
            ("رَوَٰسِىَ", "rawāsiya", "firmly set anchors", "ر س و"),
            ("تَمِيدَ", "tamīda", "it shifts / sways", "م ي د"),
        ],
        science=[
            "Like an iceberg, a mountain has a deep root. Because continental crust is lighter than the mantle beneath it, tall mountains float in it: under the Himalaya the crust is about 70 km thick, roughly twice the normal 35 km. A peak's root can reach 4 to 6 times its height.",
            "This principle, isostasy, was proposed in 1855 by George Airy and John Pratt and later confirmed by earthquake waves. In 1909 Andrija Mohorovičić found the boundary between crust and mantle, which lies deepest beneath the great ranges.",
            "Mountain belts also weld colliding plates together, like pegs fixing the edges of a tent.",
        ],
        stats=[
            ("≈70 km", "Crustal thickness under the Himalaya"),
            ("≈35 km", "Normal continental crust"),
            ("4–6×", "Typical root depth vs. mountain height"),
        ],
        discovery=("1855", "George Airy & John Pratt (isostasy)"),
        media=[
            ("File:Everest, Himalayas.jpg", "Mount Everest: the visible part of a mountain with a deep root."),
            ("File:Airy isostasy.png", "Airy isostasy: mountains float on the mantle with deep roots."),
            ("File:Isostasy.Airy&Pratt.Scheme.png", "Airy and Pratt models of isostasy, both from 1855."),
        ],
        sources=[
            ("Isostasy", "https://en.wikipedia.org/wiki/Isostasy"),
            ("Geology of the Himalayas", "https://en.wikipedia.org/wiki/Geology_of_the_Himalayas"),
            ("Mohorovičić discontinuity", "https://en.wikipedia.org/wiki/Mohorovi%C4%8Di%C4%87_discontinuity"),
        ],
    ),
    dict(
        id="water-cycle",
        title="The Water Cycle",
        hook="Winds lift clouds, rain falls “in due measure”, and springs rise: the whole cycle in three verses.",
        category="earth",
        claimType="parallel",
        verses=["30:48", "39:21", "23:18"],
        words=[
            ("بِقَدَرٍ", "bi-qadar", "in a measured amount", "ق د ر"),
            ("فَأَسْكَنَّٰهُ", "fa-askannāhu", "and We lodged it (in the earth)", "س ك ن"),
            ("يَنَٰبِيعَ", "yanābīʿa", "springs", "ن ب ع"),
        ],
        science=[
            "Sun-driven evaporation lifts about 500,000 cubic kilometres of water a year. Winds pile it into clouds, it falls as rain and snow, soaks into the ground and re-emerges as springs and rivers, over and over.",
            "This was not obvious. Many ancient and medieval thinkers believed springs were fed by seawater flowing through underground channels. In 1674 Pierre Perrault measured rainfall and river flow in the upper Seine basin and showed that rain alone was more than enough. Edme Mariotte confirmed it soon afterwards.",
            "Rain “in due measure”: globally the books balance. What evaporates comes back down. About 97% of Earth's water is salty ocean, and rain is the freshly distilled part.",
        ],
        stats=[
            ("≈500,000 km³", "Water cycled through the atmosphere each year"),
            ("97%", "Of Earth's water is in the oceans"),
            ("1674", "Perrault measures rain against river flow"),
        ],
        discovery=("1670s", "Pierre Perrault · Edme Mariotte"),
        media=[
            ("File:USGS WaterCycle Online Arabic.png", "The water cycle (USGS), in Arabic."),
            ("File:USGS WaterCycle English ONLINE 20221013.png", "The water cycle (USGS), in English."),
            ("File:The Water Cycle - Following the Water.ogv", "NASA Goddard: following the water from rain to river to sea."),
        ],
        sources=[
            ("Water cycle", "https://en.wikipedia.org/wiki/Water_cycle"),
            ("Pierre Perrault", "https://en.wikipedia.org/wiki/Pierre_Perrault"),
            ("USGS: The water cycle", "https://www.usgs.gov/water-science-school/science/water-cycle"),
        ],
    ),
    dict(
        id="hail-clouds",
        title="Clouds Piled Into Mountains",
        hook="Clouds driven gently, joined together, then piled into “mountains” that hold hail.",
        category="earth",
        claimType="parallel",
        verses=["24:43"],
        words=[
            ("يُزْجِى", "yuzjī", "drives gently", "ز ج و"),
            ("يُؤَلِّفُ", "yuʾallifu", "brings together, joins", "أ ل ف"),
            ("رُكَامًا", "rukāman", "a piled-up mass", "ر ك م"),
            ("جِبَالٍ", "jibāl", "mountains", "ج ب ل"),
            ("بَرَدٍ", "baradin", "hail", "ب ر د"),
        ],
        science=[
            "A storm cloud (cumulonimbus) grows in just this order. Small cumulus cells are pushed together by wind and convergence, merge, and stack into a tower up to 12–20 km tall: a cloud “mountain”. Rain and hail fall from deep inside the mass.",
            "Inside, updrafts stronger than 100 km/h carry water droplets up to freezing levels, where they turn to ice, get coated layer by layer and finally fall as hail.",
            "Understanding how small clouds merge into big storm clouds was built up from radar, aircraft and satellite observations in the 20th century.",
        ],
        stats=[
            ("12–20 km", "Height of a cumulonimbus top"),
            (">100 km/h", "Updrafts inside severe storm clouds"),
        ],
        discovery=("20th century", "Radar and cloud-physics research"),
        discoveryYear=1946,  # the Thunderstorm Project (see facts_evidence.py)
        media=[
            ("File:ISS-40 Thunderheads near Borneo.jpg", "Towering thunderclouds seen from the ISS."),
            ("File:Cumulonimbus sunset panorama, Albury NSW Australia.jpg", "A cumulonimbus cloud lit by the setting Sun."),
            ("File:HAIL STONES - NARA - 545891.jpg", "Hailstones."),
            ("File:Atmospheric convection over New Braunfels.webm", "Time-lapse: cumulus clouds merging and growing into cumulonimbus."),
        ],
        sources=[
            ("Cumulonimbus cloud", "https://en.wikipedia.org/wiki/Cumulonimbus_cloud"),
            ("Hail", "https://en.wikipedia.org/wiki/Hail"),
            ("Thunderstorm", "https://en.wikipedia.org/wiki/Thunderstorm"),
        ],
    ),
    dict(
        id="two-seas",
        title="A Barrier Between Two Seas",
        hook="Two seas meet side by side, and between them a barrier that neither crosses.",
        category="earth",
        claimType="parallel",
        verses=["25:53", "55:19", "55:20"],
        words=[
            ("مَرَجَ", "maraja", "He released / let flow freely", "م ر ج"),
            ("بَرْزَخًا", "barzakhan", "a barrier, a partition", "ب ر ز خ"),
            ("مَّحْجُورًا", "maḥjūran", "forbidden, walled off", "ح ج ر"),
        ],
        science=[
            "At the Strait of Gibraltar the Mediterranean (saltier, about 38 g of salt per kg) meets the Atlantic (about 36 g/kg). The denser Mediterranean water flows out along the bottom while lighter Atlantic water flows in at the surface. Density layers act as an invisible wall: the Mediterranean water keeps its own temperature and saltiness for thousands of kilometres, spreading in the Atlantic at 1,000–1,200 m depth.",
            "Where rivers meet the sea the same thing happens: light fresh water rides over a wedge of salty water with a sharp boundary. Ocean scientists call these boundaries haloclines or pycnoclines, and mixing across them is slow.",
            "Verse 25:53 mentions both kinds together: the “sweet and palatable” and the “salty and bitter”, kept apart by a barrier.",
        ],
        stats=[
            ("38 vs 36 g/kg", "Salinity: Mediterranean vs. Atlantic"),
            ("1,000–1,200 m", "Depth at which Mediterranean water spreads in the Atlantic"),
        ],
        media=[
            ("File:Strait of Gibraltar (MODIS 2016-12-30).jpg", "The Strait of Gibraltar, where the Mediterranean meets the Atlantic (NASA MODIS)."),
            ("File:InternalWaves Gibraltar ISS009-E-09952 54 (detail).jpg", "Internal waves at the Strait of Gibraltar, on the boundary between the two waters."),
            ("File:Gibraltar (Satellite picture).jpg", "Gibraltar from space (ESA astronaut Alexander Gerst)."),
            ("File:Thermohaline conveyor belt (NASA).webm", "NASA: how salty, dense water sinks and flows through the world's oceans."),
        ],
        sources=[
            ("Strait of Gibraltar", "https://en.wikipedia.org/wiki/Strait_of_Gibraltar"),
            ("Halocline", "https://en.wikipedia.org/wiki/Halocline"),
            ("Pycnocline", "https://en.wikipedia.org/wiki/Pycnocline"),
        ],
    ),
    dict(
        id="deep-sea-darkness",
        title="Darkness in the Deep Sea",
        hook="Darkness in a deep sea, wave upon wave, and clouds above, a description of the ocean's layers.",
        category="earth",
        claimType="interpretive",
        verses=["24:40"],
        words=[
            ("لُّجِّىٍّ", "lujjiyyin", "vast and deep (of the sea)", "ل ج ج"),
            ("مَوْجٌ", "mawj", "a wave", "م و ج"),
            ("ظُلُمَٰتٌۢ", "ẓulumātun", "darknesses", "ظ ل م"),
        ],
        science=[
            "Sunlight fades fast underwater. Red light vanishes within about 10 m, almost no light remains below 200 m, and below 1,000 m the ocean is in permanent darkness, except for the light that animals make themselves.",
            "The phrase “wave upon wave” has a second meaning that oceanographers discovered later. As well as surface waves, the ocean has internal waves: enormous waves that travel along the boundaries between density layers beneath the surface, invisible from above. They can reach 100 m in height. Fridtjof Nansen's ship met their drag (“dead water”) in 1893, and Vagn Ekman explained it in 1904.",
            "Humans saw the dark ocean directly only in 1934, when William Beebe and Otis Barton descended 923 m in the bathysphere.",
        ],
        stats=[
            ("200 m", "Depth below which almost no sunlight remains"),
            ("1,000 m", "Depth of permanent darkness"),
            ("100 m", "Height of the largest internal waves"),
        ],
        discovery=("1904 – 1934", "Ekman (internal waves) · Beebe (deep dive)"),
        media=[
            ("File:Ocean zones.png", "Ocean light zones: sunlit, twilight and midnight."),
            ("File:Oceanic nonlinear internal solitary waves from the Lombok Strait (MODIS 2016-11-05).jpg", "Internal waves visible from space in the Lombok Strait (NASA MODIS)."),
            ("File:Ex1402-dive01.webm", "NOAA's Okeanos Explorer films the deep sea floor."),
        ],
        sources=[
            ("Internal wave", "https://en.wikipedia.org/wiki/Internal_wave"),
            ("Photic zone", "https://en.wikipedia.org/wiki/Photic_zone"),
            ("Dead water", "https://en.wikipedia.org/wiki/Dead_water"),
        ],
    ),
    dict(
        id="fertilizing-winds",
        title="The Fertilizing Winds",
        hook="“We sent the fertilizing winds”: they carry pollen, and the seeds of rain.",
        category="earth",
        claimType="parallel",
        verses=["15:22"],
        words=[
            ("لَوَٰقِحَ", "lawāqiḥa", "fertilizing, impregnating", "ل ق ح"),
        ],
        science=[
            "Wind is nature's courier. Wheat, maize, rice, grasses and most conifers rely on wind to carry pollen from male to female flowers.",
            "Winds also “fertilize” clouds. They lift sea salt and dust into the air, and these tiny particles act as nuclei around which cloud droplets condense. No nuclei, no rain.",
            "The verse goes on to link the two: after the winds, “We sent down water from the sky and gave it to you to drink.”",
        ],
        stats=[
            ("1694", "Camerarius proves plant sex (wind pollination follows)"),
        ],
        media=[
            ("File:Pine releasing pollen into the wind in Tuntorp 1.jpg", "A pine tree releasing a cloud of pollen into the wind."),
            ("File:A cloud of tree pollen - geograph.org.uk - 5755690.jpg", "A cloud of tree pollen drifting on the wind."),
        ],
        sources=[
            ("Pollination", "https://en.wikipedia.org/wiki/Pollination"),
            ("Anemophily (wind pollination)", "https://en.wikipedia.org/wiki/Anemophily"),
            ("Cloud condensation nuclei", "https://en.wikipedia.org/wiki/Cloud_condensation_nuclei"),
        ],
    ),

    # ======================= LIVING THINGS =======================
    dict(
        id="life-from-water",
        title="Life From Water",
        hook="“We made from water every living thing.”",
        category="life",
        claimType="parallel",
        verses=["21:30", "24:45"],
        words=[
            ("ٱلْمَآءِ", "al-māʾ", "water", "م و ه"),
            ("حَىٍّ", "ḥayy", "living", "ح ي ي"),
            ("دَآبَّةٍ", "dābbatin", "a creature that moves", "د ب ب"),
        ],
        science=[
            "Every living thing on Earth depends on liquid water. Water is roughly 60% of an adult human's body weight and about 70% of a cell's contents. Some jellyfish are 95% water.",
            "Life itself probably began in water. The earliest traces of life, in rocks 3.5 billion years old, formed in ancient seas or hot springs. In the 1920s Alexander Oparin and J. B. S. Haldane independently proposed that life arose in a watery “primordial soup”.",
            "It is why space agencies “follow the water” in the search for life on Mars and on Jupiter's moon Europa.",
        ],
        stats=[
            ("≈60%", "Water in an adult human body"),
            ("≈70%", "Water inside a typical cell"),
            ("3.5 bn yrs", "Age of the oldest traces of life"),
        ],
        discovery=("1920s", "Oparin & Haldane (life began in water)"),
        media=[
            ("File:Water molecule.jpg", "A water molecule, H₂O."),
            ("File:Blue Marble Western Hemisphere.jpg", "Earth, the water planet (NASA)."),
        ],
        sources=[
            ("Properties of water", "https://en.wikipedia.org/wiki/Properties_of_water"),
            ("Abiogenesis", "https://en.wikipedia.org/wiki/Abiogenesis"),
            ("USGS: The water in you", "https://www.usgs.gov/water-science-school/science/water-you-water-and-human-body"),
        ],
    ),
    dict(
        id="honey-bee",
        title="The Bee Is Addressed as Female",
        hook="The Quran commands the bee in the feminine, and every foraging honeybee is female.",
        category="insects",
        claimType="parallel",
        verses=["16:68", "16:69"],
        words=[
            ("ٱتَّخِذِى", "ittakhidhī", "take up! (feminine command)", "أ خ ذ"),
            ("كُلِى", "kulī", "eat! (feminine command)", "أ ك ل"),
            ("فَٱسْلُكِى", "fa-sluki", "and follow! (feminine command)", "س ل ك"),
            ("شِفَآءٌ", "shifāʾun", "healing", "ش ف ي"),
        ],
        science=[
            "In a hive, the workers that fly out to gather nectar are all female. Males (drones) do not forage; they exist to mate. Arabic marks gender in commands, and the verbs ittakhidhī (take up!), kulī (eat!) and fasluki (follow!) are all feminine forms.",
            "For centuries Europeans spoke of a “king bee”. Jan Swammerdam's dissections in the 1660s revealed that the ruler of the hive was a queen with ovaries; the workers were later understood to be females too.",
            "“A drink of varying colours, in which is healing”: honey ranges from near-white to almost black depending on the flowers. It is acidic (pH about 3.9), very low in water, and its enzymes make hydrogen peroxide, which is why medical-grade honey is used on wounds today.",
        ],
        stats=[
            ("100% ♀", "Of foraging honeybees are female"),
            ("pH ≈ 3.9", "Acidity of honey"),
            ("1660s", "Swammerdam shows the “king” is a queen"),
        ],
        discovery=("1660s", "Jan Swammerdam"),
        media=[
            ("File:Honey Bee Collecting Pollen.jpg", "A worker honeybee (female) collecting pollen."),
            ("File:Honeycomb on a tree.jpg", "A wild honeycomb built in a tree: “houses in the trees”."),
            ("File:Western honey bee (Apis mellifera).webm", "A honeybee at work."),
        ],
        sources=[
            ("Honey bee", "https://en.wikipedia.org/wiki/Honey_bee"),
            ("Jan Swammerdam", "https://en.wikipedia.org/wiki/Jan_Swammerdam"),
            ("Honey: medicinal and antibacterial properties", "https://en.wikipedia.org/wiki/Honey"),
        ],
    ),
    dict(
        id="milk-from-blood",
        title="Milk, Between Digestion and Blood",
        hook="Pure milk comes from “between excretion and blood”: nutrients pass from the gut into the blood, and from the blood into milk.",
        category="life",
        claimType="interpretive",
        verses=["16:66"],
        words=[
            ("فَرْثٍ", "farth", "partly digested matter in the gut", "ف ر ث"),
            ("دَمٍ", "dam", "blood", "د م م"),
            ("سَآئِغًا", "sāʾighan", "pleasant, easily swallowed", "س و غ"),
        ],
        science=[
            "Milk is made in the mammary gland from nutrients that the intestines absorb from digested food and the blood carries to the udder. The gland's cells draw amino acids, sugars and fats from the blood and turn them into milk. A cow must pump roughly 400–500 litres of blood through her udder to make one litre of milk.",
            "So milk really does come from “between” the two: nutrients from the gut, then blood, then the gland.",
        ],
        stats=[
            ("400–500 L", "Blood pumped through a cow's udder for 1 L of milk"),
        ],
        media=[
            ("File:Cow female black white.jpg", "A dairy cow (USDA)."),
            ("File:Cows at an organic farm.jpg", "Cows grazing: “in grazing livestock is a lesson for you”."),
            ("File:Milk under the microscope.webm", "Milk under the microscope."),
        ],
        sources=[
            ("Lactation", "https://en.wikipedia.org/wiki/Lactation"),
            ("Mammary gland", "https://en.wikipedia.org/wiki/Mammary_gland"),
        ],
    ),

    # ======================= HUMAN CREATION =======================
    dict(
        id="embryo-stages",
        title="Stages in the Womb",
        hook="A drop, a clinging structure, a chewed-looking lump, bones clothed with flesh: the womb's stages in one verse.",
        category="human",
        claimType="parallel",
        verses=["23:12", "23:13", "23:14"],
        words=[
            ("نُطْفَةً", "nuṭfatan", "a small drop", "ن ط ف"),
            ("عَلَقَةً", "ʿalaqatan", "something that clings", "ع ل ق"),
            ("مُضْغَةً", "muḍghatan", "a chewed-like morsel", "م ض غ"),
            ("ٱلْعِظَٰمَ", "al-ʿiẓām", "the bones", "ع ظ م"),
            ("لَحْمًا", "laḥman", "flesh", "ل ح م"),
        ],
        science=[
            "After fertilisation the egg divides into a ball of cells, the blastocyst, a tiny “drop” (nuṭfah). About 6 to 10 days later it settles in the “firm lodging” of the womb (23:13) and implants: it literally clings to the uterine wall (ʿalaqah, “that which clings”).",
            "Between about days 20 and 30 after fertilisation, the embryo is a small curved body lined along its back with rounded blocks of tissue called somites. Many find the resemblance to a chewed morsel (muḍghah) striking.",
            "Cartilage models of the bones form and begin to harden around weeks 6–8 after fertilisation while muscle grows around them. By eight weeks all the major organs are present and the embryo looks recognisably human, “another creation” (khalqan ākhar).",
        ],
        stats=[
            ("6–10 days", "Implantation: the embryo “clings” to the womb"),
            ("Days 20–30", "Somites form along the embryo's back"),
            ("8 weeks", "All major organs formed"),
        ],
        discovery=("1651 – 1876", "Harvey · Malpighi · von Baer · Hertwig"),
        media=[
            ("File:Human Embryo - Approximately 8 weeks estimated gestational age.jpg", "A human embryo at about 8 weeks (museum specimen)."),
            ("File:Embryogenesis1.jpg", "Diagram of early embryonic development."),
            ("File:Yolk sac human.jpg", "A human embryo at the implantation stage, showing the yolk sac."),
        ],
        sources=[
            ("Human embryonic development", "https://en.wikipedia.org/wiki/Human_embryonic_development"),
            ("Implantation (human embryo)", "https://en.wikipedia.org/wiki/Implantation_(embryology)"),
            ("Somite", "https://en.wikipedia.org/wiki/Somite"),
        ],
    ),
    dict(
        id="sex-determination",
        title="Male or Female from a Drop",
        hook="Male or female is decided by the sperm-drop, a fact biology confirmed in 1905.",
        category="human",
        claimType="parallel",
        verses=["53:45", "53:46", "75:37", "75:38", "75:39"],
        words=[
            ("تُمْنَىٰ", "tumnā", "is emitted", "م ن ي"),
            ("ٱلزَّوْجَيْنِ", "az-zawjayn", "the two mates", "ز و ج"),
            ("ٱلذَّكَرَ", "adh-dhakar", "the male", "ذ ك ر"),
            ("وَٱلْأُنثَىٰ", "wal-unthā", "and the female", "أ ن ث"),
        ],
        science=[
            "Humans have 23 pairs of chromosomes. Every egg carries an X. A sperm carries either an X (a girl) or a Y (a boy). So the sex of a child is determined by the father's sperm.",
            "Nettie Stevens and Edmund Wilson identified the sex chromosomes in 1905. The SRY gene on the Y chromosome that switches on male development was found in 1990.",
            "In many societies mothers have been blamed for having daughters. The verse assigns the determining role to the drop, “when it is emitted”.",
        ],
        stats=[
            ("XX / XY", "Female / male chromosome pairs"),
            ("1905", "Sex chromosomes identified"),
            ("1990", "SRY, the male-development gene, found"),
        ],
        discovery=("1905", "Nettie Stevens & Edmund B. Wilson"),
        media=[
            ("File:Human karyotype diagram showing autosomes and sex chromosomes - NHGRI.jpg", "The 23 human chromosome pairs; the last pair (X and Y) decides sex."),
            ("File:Human male karyotype.gif", "A male karyotype with its X and Y chromosomes (NHGRI)."),
        ],
        sources=[
            ("XY sex-determination system", "https://en.wikipedia.org/wiki/XY_sex-determination_system"),
            ("Nettie Stevens", "https://en.wikipedia.org/wiki/Nettie_Stevens"),
            ("SRY gene", "https://en.wikipedia.org/wiki/SRY"),
        ],
    ),
    dict(
        id="senses-order",
        title="Hearing, Then Sight",
        hook="The Quran lists hearing before sight, the order in which the senses awaken before birth.",
        category="human",
        claimType="interpretive",
        verses=["32:9", "76:2"],
        words=[
            ("ٱلسَّمْعَ", "as-samʿ", "hearing", "س م ع"),
            ("ٱلْأَبْصَٰرَ", "al-abṣār", "sight (eyes)", "ب ص ر"),
            ("وَٱلْأَفْـِٔدَةَ", "wal-afʾidah", "and the hearts / understanding", "ف أ د"),
        ],
        science=[
            "The inner ear is working by about 18–20 weeks of pregnancy, and the fetus first responds to sound around week 19 (Hepper & Shahidullah, 1994). The eyelids fuse shut at about 10 weeks and reopen only around 26 weeks.",
            "After birth, newborns already recognise their mother's voice (DeCasper & Fifer, 1980): the sound they heard in the womb.",
            "The Quran lists “hearing” before “sight” in almost every place the two occur, and in 32:9 adds “hearts”, the seat of understanding, third.",
        ],
        stats=[
            ("≈19 weeks", "First fetal responses to sound"),
            ("≈26 weeks", "Eyelids reopen"),
        ],
        discovery=("1980 – 1994", "DeCasper & Fifer · Hepper & Shahidullah"),
        media=[
            ("File:Human Embryo - Approximately 8 weeks estimated gestational age.jpg", "A human embryo at about 8 weeks, before the senses have begun to work."),
        ],
        sources=[
            ("Prenatal development", "https://en.wikipedia.org/wiki/Prenatal_development"),
            ("Development of fetal hearing (Hepper & Shahidullah, 1994)", "https://pubmed.ncbi.nlm.nih.gov/7979483/"),
            ("Newborns prefer their mothers' voices (DeCasper & Fifer, 1980)", "https://pubmed.ncbi.nlm.nih.gov/7375928/"),
        ],
    ),
    dict(
        id="fingerprints",
        title="The Fingertips",
        hook="God is able to reassemble the bones, “down to the very tips of the fingers.”",
        category="human",
        claimType="interpretive",
        verses=["75:3", "75:4"],
        words=[
            ("بَنَانَهُۥ", "banānahū", "his fingertips", "ب ن ن"),
            ("نُّسَوِّىَ", "nusawwiya", "to shape perfectly", "س و ي"),
        ],
        science=[
            "Fingerprint ridges form in the womb between weeks 10 and 17 and stay the same for life. They differ between individuals, even identical twins.",
            "Francis Galton's 1892 book “Finger Prints” established scientifically that they are permanent and unique, which opened the door to forensic identification. Before that, fingerprints were pressed into clay and used as personal marks in ancient Babylon and China.",
            "The verse, in the context of resurrection, says God is able to put together even the banān, the fingertips: the finest and most individual detail of a person.",
        ],
        stats=[
            ("10–17 weeks", "When the fingerprint pattern forms"),
            ("1892", "Galton proves permanence and uniqueness"),
        ],
        discovery=("1892", "Francis Galton"),
        media=[
            ("File:Fingerprint - Plain Whorl.jpg", "A fingerprint: a plain whorl pattern."),
            ("File:Fingerprint - Central Pocket Loop Whorl.jpg", "A fingerprint: a central pocket loop whorl."),
        ],
        sources=[
            ("Fingerprint", "https://en.wikipedia.org/wiki/Fingerprint"),
            ("Francis Galton", "https://en.wikipedia.org/wiki/Francis_Galton"),
        ],
    ),

    # ======================= SIGNS & HISTORY =======================
    dict(
        id="moon-split",
        title="The Splitting of the Moon",
        hook="“The Hour has come near, and the moon has split.” A sign in the Islamic tradition, not a scientific discovery.",
        category="signs",
        claimType="miracle",
        scienceHeading="What we can and cannot say",
        verses=["54:1", "54:2"],
        words=[
            ("ٱقْتَرَبَتِ", "iqtarabat", "has drawn near", "ق ر ب"),
            ("ٱنشَقَّ", "inshaqqa", "split, was cleft in two", "ش ق ق"),
        ],
        science=[
            "Muslim scholars have long held that this event happened at Makkah during the Prophet's ﷺ lifetime, when the disbelievers demanded a sign: the Moon appeared split in two, with the mountain seen between the two parts. It is reported in Sahih al-Bukhari (for example 3636, 3637 and 4864) and in Sahih Muslim, from companions including Anas ibn Malik and Abdullah ibn Masʿud.",
            "This is a miracle in the Islamic tradition: an event given as a sign, not a natural process. Science studies repeatable natural processes, so it can neither prove nor disprove it, and this app does not claim otherwise.",
            "You may have seen posts claiming that “NASA found a crack across the Moon” that proves it. No NASA statement supports that. The long channels seen on the Moon are lunar rilles, ordinary features made by ancient lava flows and faulting. We do not use that story.",
        ],
        stats=[],
        media=[
            ("File:Full Moon Luc Viatour.jpg", "The Full Moon."),
            ("File:Tour of the Moon in 4K.webm", "NASA's Lunar Reconnaissance Orbiter: a tour of the Moon's surface."),
        ],
        sources=[
            ("Sahih al-Bukhari 3636", "https://sunnah.com/bukhari:3636"),
            ("Sahih al-Bukhari 3637", "https://sunnah.com/bukhari:3637"),
            ("Sahih al-Bukhari 4864", "https://sunnah.com/bukhari:4864"),
            ("Lunar rilles (what the Moon's “cracks” really are)", "https://en.wikipedia.org/wiki/Rille"),
        ],
    ),
    dict(
        id="roman-prophecy",
        title="The Romans Will Win",
        hook="A prophecy about a superpower war: the defeated Romans (ar-Rūm, the Byzantines) “will overcome”, within “three to nine years”.",
        category="signs",
        claimType="historical",
        scienceHeading="What history records",
        verses=["30:2", "30:3", "30:4"],
        words=[
            ("أَدْنَى", "adnā", "nearest / lowest", "د ن و"),
            ("بِضْعِ", "biḍʿi", "a few (in Arabic, 3 to 9)", "ب ض ع"),
        ],
        science=[
            "In 614 CE Sasanian Persian armies took Jerusalem from the Byzantine (Roman) Empire, and by 619 they had conquered Egypt. The Byzantines seemed finished. In Makkah the small, persecuted Muslim community sympathised with the Christian Romans against the Persians.",
            "From 622 Emperor Heraclius launched a counter-offensive. At the decisive Battle of Nineveh in 627 the Persian army was crushed, and by 628 the war was over: the reversal the verses foretold.",
            "“Adnā al-arḍ” can mean “the nearest land” or “the lowest land”. The Dead Sea region, the lowest exposed land on Earth (about 430 m below sea level), lay in the war zone.",
        ],
        stats=[
            ("614 CE", "Persians take Jerusalem"),
            ("627 CE", "Byzantines win at Nineveh"),
            ("3–9 years", "What biḍʿ means in Arabic"),
        ],
        media=[
            ("File:Sasanian Empire 621 A.D.jpg", "The Sasanian Persian Empire at its greatest extent, about 621 CE."),
            ("File:Dead Sea from Jordan.JPG", "The Dead Sea, the lowest exposed land on Earth."),
        ],
        sources=[
            ("Byzantine–Sasanian War of 602–628", "https://en.wikipedia.org/wiki/Byzantine%E2%80%93Sasanian_War_of_602%E2%80%93628"),
            ("Battle of Nineveh (627)", "https://en.wikipedia.org/wiki/Battle_of_Nineveh_(627)"),
            ("Dead Sea", "https://en.wikipedia.org/wiki/Dead_Sea"),
        ],
    ),
]


# Second and third batches (see facts_more.py for the inclusion rule)
from facts_more import MORE_FACTS  # noqa: E402
from facts_third import THIRD_FACTS  # noqa: E402

FACTS = FACTS + MORE_FACTS + THIRD_FACTS

# The Quran's own statement of what this app is about, shown on the home screen.
INTRO_VERSE = "41:53"
