# -*- coding: utf-8 -*-
"""
Facts added in the second batch. Rule for inclusion (set by the app's owner): a fact is here only if
  (1) it is written in a verse of the Quran, and
  (2) people have actually discovered or measured the thing it points to.
Anything without a verse behind it (for example hexagonal honeycomb, or ants burying their dead) is NOT
here, and anything that has not actually been discovered (for example the identity of the pharaoh of
the Exodus) is not here either. Same editorial rules as facts_source.py apply.
"""

MORE_FACTS = [
    # ============================ INSECTS & SMALL CREATURES ============================
    dict(
        id="ant-valley",
        title="The Ant Who Warned Her Colony",
        hook="An ant says, “O ants, enter your homes”, and biologists have since shown that ants really do warn and call one another.",
        category="insects",
        claimType="interpretive",
        verses=["27:18"],
        words=[
            ("نَمْلَةٌ", "namlatun", "an ant (a feminine noun in Arabic)", "ن م ل"),
            ("ٱلنَّمْلِ", "an-naml", "the ants", "ن م ل"),
            ("مَسَٰكِنَكُمْ", "masākinakum", "your dwellings", "س ك ن"),
            ("لَا يَحْطِمَنَّكُمْ", "lā yaḥṭimannakum", "lest they crush you", "ح ط م"),
        ],
        science=[
            "Ants live in colonies of thousands to millions and coordinate through chemicals, touch and sound. Alarm chemicals send nestmates rushing to shelter or to defend it, and trail chemicals mark the way to food.",
            "The word “pheromone” was coined in 1959. In 1962 E. O. Wilson showed how fire ants lay a chemical trail from food to the nest. In 1965 Hubert Markl found that leaf-cutter ants trapped underground rub a ridge on their bodies to make sounds that call nestmates to dig them out.",
            "Some ants build enormous nations: one supercolony of Argentine ants stretches about 6,000 km along the European coast, from Italy to Spain.",
        ],
        stats=[
            ("1959", "The word “pheromone” is coined"),
            ("1962", "Fire-ant trail signals decoded (Wilson)"),
            ("1965", "Leaf-cutter ants found to call for help by sound"),
        ],
        discovery=("1959 – 1965", "Karlson & Lüscher · E. O. Wilson · Hubert Markl"),
        media=[
            ("File:Black garden ant (51503768140).jpg", "A black garden ant worker."),
            ("File:Iz - Leafcutter ants 1.jpg", "Leaf-cutter ants carrying leaf pieces along a trail."),
            ("File:Leafcutter ants on the move.webm", "Leaf-cutter ants on the move in Trinidad."),
        ],
        sources=[
            ("Ant", "https://en.wikipedia.org/wiki/Ant"),
            ("Pheromone", "https://en.wikipedia.org/wiki/Pheromone"),
            ("Eusociality", "https://en.wikipedia.org/wiki/Eusociality"),
        ],
    ),
    dict(
        id="bee-dance",
        title="The Bee's Paths Made Easy",
        hook="Told to “follow the ways of your Lord made easy”, a bee can fly kilometres and tell its sisters exactly where the flowers are, by dancing.",
        category="insects",
        claimType="interpretive",
        verses=["16:68", "16:69"],
        words=[
            ("وَأَوْحَىٰ", "wa-awḥā", "and He inspired", "و ح ي"),
            ("سُبُلَ", "subula", "the ways, the paths", "س ب ل"),
            ("ذُلُلًا", "dhululan", "made smooth and easy", "ذ ل ل"),
        ],
        science=[
            "A forager that finds rich flowers returns to the hive and performs a waggle dance: a figure-of-eight run. The angle of the straight run, measured against the vertical comb, tells the direction of the flowers relative to the Sun, and its length tells the distance.",
            "Karl von Frisch decoded the dance in the 1940s and shared the 1973 Nobel Prize for it. In 2005 harmonic radar tracked bees leaving the hive after watching a dance and showed that they really flew to the place the dance described.",
            "Bees find their way by the Sun, by the pattern of polarised light in the sky and by landmarks, and can forage several kilometres from the hive.",
        ],
        stats=[
            ("1973", "Nobel Prize for decoding the bee dance"),
            ("2005", "Radar shows bees really follow the dance"),
            ("1989", "A robot bee successfully recruits real foragers"),
        ],
        discovery=("1940s – 2005", "Karl von Frisch · Riley et al."),
        media=[
            ("File:Bee waggle dance.png", "How the waggle dance encodes direction and distance."),
            ("File:Bee waggle dance task.jpg", "Bees on the comb: a dancer is surrounded by followers."),
            ("File:Waggle Dance.webm", "Bees performing the waggle dance."),
        ],
        sources=[
            ("Waggle dance", "https://en.wikipedia.org/wiki/Waggle_dance"),
            ("Karl von Frisch", "https://en.wikipedia.org/wiki/Karl_von_Frisch"),
            ("The Nobel Prize 1973", "https://www.nobelprize.org/prizes/medicine/1973/summary/"),
        ],
    ),
    dict(
        id="locust-swarm",
        title="Like Scattered Locusts",
        hook="People rise “like locusts spread out”, and locust science shows how shy solitary insects become a swarm within hours.",
        category="insects",
        claimType="interpretive",
        verses=["54:7", "7:133"],
        words=[
            ("جَرَادٌ", "jarādun", "locusts", "ج ر د"),
            ("مُّنتَشِرٌ", "muntashirun", "spread out, scattered", "ن ش ر"),
            ("ٱلْجَرَادَ", "al-jarād", "the locusts", "ج ر د"),
        ],
        science=[
            "A swarm of desert locusts can hold 40 to 80 million adults per square kilometre. Each locust eats about its own weight in food a day, so one square kilometre of swarm eats what 35,000 people would in a day, and swarms can be carried up to about 150 km in a day on the wind.",
            "For a long time solitary grasshoppers and swarming locusts were thought to be different animals. In 1921 Boris Uvarov showed they are one species in two forms, called phases.",
            "In 2009 a team led by Michael Anstey and Stephen Simpson showed what flips the switch: crowding, especially the touch of other locusts on the hind legs, raises serotonin in the nervous system. Within about two hours a shy solitary insect becomes gregarious, and blocking serotonin stops the change.",
        ],
        stats=[
            ("40–80 M", "Locusts per km² of swarm (FAO)"),
            ("≈35,000", "People whose daily food one km² of swarm eats"),
            ("≈2 hours", "For a solitary locust to turn gregarious (2009)"),
        ],
        discovery=("1921 – 2009", "Boris Uvarov · Michael Anstey & Stephen Simpson"),
        media=[
            ("File:Desert Locust swarm.jpg", "A swarm of desert locusts."),
            ("File:Aggregation Site Choice by Gregarious Nymphs of the Desert Locust, Schistocerca gregaria, in the Sahara Desert of Mauritania Figure 1.jpg", "Gregarious desert locust nymphs roosting together in Mauritania."),
            ("File:Locusta migratoria female flying HighSpeedPhoto.webm", "A locust in flight, filmed at high speed."),
        ],
        sources=[
            ("Desert locust", "https://en.wikipedia.org/wiki/Desert_locust"),
            ("FAO: Desert locust", "https://www.fao.org/locusts/faqs/en/"),
            ("Serotonin mediates behavioral gregarization (Science, 2009)", "https://pubmed.ncbi.nlm.nih.gov/19179529/"),
        ],
    ),
    dict(
        id="fly-creation",
        title="Not Even a Fly",
        hook="No one has ever created a fly. Scientists have now mapped a fly's whole brain, and still cannot build one.",
        category="insects",
        claimType="interpretive",
        verses=["22:73"],
        words=[
            ("ذُبَابًا", "dhubāban", "a fly", "ذ ب ب"),
            ("يَخْلُقُوا۟", "yakhluqū", "they could create", "خ ل ق"),
            ("يَسْلُبْهُمُ", "yaslubhumu", "snatches from them", "س ل ب"),
            ("يَسْتَنقِذُوهُ", "yastanqidhūhu", "recover it", "ن ق ذ"),
        ],
        science=[
            "A housefly eats solid food by spitting digestive fluid onto it, letting it dissolve, and sucking it up. Fruit flies are among the best-studied animals on Earth: since Thomas Hunt Morgan's “fly room” of 1910 they have taught us how genes, chromosomes and development work.",
            "In 2024 the FlyWire project published the complete wiring diagram of a fruit fly's brain: 139,255 neurons and more than 50 million connections between them. It is one of the great mapping feats of biology.",
            "Yet nobody has built a fly, or even a living cell, from non-living parts. In 2010 Craig Venter's team made a bacterium run on a chemically synthesised genome, but they had to put that genome into an existing cell.",
        ],
        stats=[
            ("139,255", "Neurons mapped in a fly's brain (2024)"),
            (">50 million", "Connections between them"),
            ("2010", "First cell run by a synthetic genome, in an existing cell"),
        ],
        discovery=("1665 – 2024", "Hooke · Morgan · Venter · FlyWire consortium"),
        media=[
            ("File:Housefly on a leaf crop.jpg", "A housefly."),
            ("File:Drosophilidae compound eye edit1.jpg", "The compound eye of a fruit fly."),
            ("File:Stubenfliege mit Speichelblase?.ogg", "A housefly with a bubble of saliva: it digests food outside its body."),
        ],
        sources=[
            ("Housefly", "https://en.wikipedia.org/wiki/Housefly"),
            ("Drosophila melanogaster", "https://en.wikipedia.org/wiki/Drosophila_melanogaster"),
            ("Neuronal wiring diagram of an adult brain (Nature, 2024)", "https://www.nature.com/articles/s41586-024-07558-y"),
        ],
    ),
    dict(
        id="mosquito-example",
        title="The Mosquito and What Is Above It",
        hook="God is not ashamed to use a mosquito as an example, and the smallest creature can be the deadliest.",
        category="insects",
        claimType="interpretive",
        verses=["2:26"],
        words=[
            ("بَعُوضَةً", "baʿūḍatan", "a gnat, a mosquito", "ب ع ض"),
            ("فَمَا فَوْقَهَا", "famā fawqahā", "or what is above it", "ف و ق"),
            ("يَسْتَحْىِۦٓ", "yastaḥyī", "is ashamed, shrinks from", "ح ي ي"),
        ],
        science=[
            "Female mosquitoes carry malaria, dengue, yellow fever and Zika. Malaria alone still kills about 600,000 people a year, most of them young children (WHO).",
            "That the mosquito is the carrier was found out between 1880 and 1900. In 1880 Alphonse Laveran saw the malaria parasite in human blood. In 1897 Ronald Ross found its developing stages in the gut of an Anopheles mosquito, work that won the 1902 Nobel Prize. In 1900 an American commission under Walter Reed confirmed that yellow fever is also spread by mosquitoes.",
            "A mosquito also carries something far smaller than itself: the malaria parasite is a single cell a few thousandths of a millimetre long.",
        ],
        stats=[
            ("≈600,000", "Malaria deaths a year (WHO)"),
            ("1897", "Ross finds malaria parasites in a mosquito"),
            ("1902", "Nobel Prize for that discovery"),
        ],
        discovery=("1880 – 1900", "Laveran · Ross · Reed"),
        media=[
            ("File:AnophelesGambiaemosquito.jpg", "An Anopheles gambiae mosquito, the main carrier of malaria in Africa (CDC)."),
            ("File:Aedes aegypti CDC-Gathany.jpg", "Aedes aegypti, which carries dengue and yellow fever (CDC)."),
            ("File:Ronald Ross, Observations on malaria Wellcome L0026902.jpg", "A page from Ronald Ross's malaria observations (Wellcome Collection)."),
            ("File:Culicidae larvae in motion.webm", "Mosquito larvae in water."),
        ],
        sources=[
            ("Mosquito", "https://en.wikipedia.org/wiki/Mosquito"),
            ("Ronald Ross", "https://en.wikipedia.org/wiki/Ronald_Ross"),
            ("WHO: Malaria", "https://www.who.int/news-room/fact-sheets/detail/malaria"),
        ],
    ),
    dict(
        id="spider-house",
        title="The Spider's House",
        hook="“The weakest of houses is the spider's house”: its silk is tougher than steel, but as a home it protects nothing.",
        category="insects",
        claimType="interpretive",
        verses=["29:41"],
        words=[
            ("ٱلْعَنكَبُوتِ", "al-ʿankabūt", "the spider", "ع ن ك ب"),
            ("ٱتَّخَذَتْ", "ittakhadhat", "she took (feminine)", "أ خ ذ"),
            ("بَيْتًا", "baytan", "a house", "ب ي ت"),
            ("أَوْهَنَ", "awhana", "the weakest, the frailest", "و ه ن"),
        ],
        science=[
            "Spider silk is one of the toughest materials known. Weight for weight it rivals steel and beats Kevlar for toughness. In 2010 researchers measured the silk of Darwin's bark spider as the toughest biological material ever tested.",
            "But a web is a trap, not a shelter. It gives no protection from rain, wind, cold or enemies, and orb-weaving spiders often eat and respin theirs every day. As a house, it is truly frail.",
            "Biologists have also documented sexual cannibalism, where a female spider kills and eats her mate, in many species. Some modern writers read that into the verse's picture of a spider's household; classical commentators did not.",
        ],
        stats=[
            ("2010", "Silk of Darwin's bark spider measured as the toughest known"),
            ("1709", "First spider-silk stockings and gloves (Bon)"),
        ],
        discovery=("1709 – 2010", "Bon · McCook · Agnarsson"),
        media=[
            ("File:Argiope lobata, female - Sète 03.jpg", "A female Argiope spider on her web."),
            ("File:Golden orb-weavers (Nephila pilipes) female and male Double Haven.jpg", "A large female golden orb-weaver and a tiny male."),
            ("File:Garden spider web weaving.webm", "A garden spider spinning her web."),
        ],
        sources=[
            ("Spider silk", "https://en.wikipedia.org/wiki/Spider_silk"),
            ("Spider web", "https://en.wikipedia.org/wiki/Spider_web"),
            ("Sexual cannibalism", "https://en.wikipedia.org/wiki/Sexual_cannibalism"),
        ],
    ),

    # ============================ LIVING THINGS ============================
    dict(
        id="animal-communities",
        title="Communities Like You",
        hook="“No creature on the earth, nor bird that flies with its wings, but is a community like you.” Zoologists have found societies in insects, birds and mammals.",
        category="life",
        claimType="interpretive",
        verses=["6:38"],
        words=[
            ("دَآبَّةٍ", "dābbatin", "a creature that moves on the earth", "د ب ب"),
            ("بِجَنَاحَيْهِ", "bi-janāḥayhi", "with its two wings", "ج ن ح"),
            ("أُمَمٌ", "umamun", "communities, nations, kinds", "أ م م"),
            ("أَمْثَالُكُم", "amthālukum", "like you", "م ث ل"),
        ],
        science=[
            "Bees, ants and termites live in true societies with castes, division of labour and shared nests. E. O. Wilson's The Insect Societies (1971) laid out how they work.",
            "Birds and mammals have societies too. In a starling murmuration each bird tracks about seven neighbours (2008). Vervet monkeys use different alarm calls for different predators (1980). Elephants call to each other in infrasound, too low for us to hear (1986). Bees tell each other where flowers are by dancing.",
            "The picture that emerges is that even a tiny creature is part of a group with signals, roles and shared knowledge, which is close to what the verse says.",
        ],
        stats=[
            ("1971", "The Insect Societies (Wilson)"),
            ("1980", "Vervet monkeys found to have predator-specific alarm calls"),
            ("≈7", "Neighbours each starling tracks in a flock (2008)"),
        ],
        discovery=("1946 – 2008", "von Frisch · Seyfarth & Cheney · Ballerini et al."),
        media=[
            ("File:A murmuration of starlings - geograph.org.uk - 4288342.jpg", "A murmuration of starlings."),
            ("File:Elephant Herd (139026597).jpeg", "An elephant herd."),
            ("File:Meerkat (Suricata suricatta).jpg", "A meerkat standing guard."),
            ("File:Litchfield National Park (AU), Magnetic Termite Mounds -- 2019 -- 3721.jpg", "Termite mounds: the work of a whole society."),
            ("File:Flock of starlings (Sturnus vulgaris).webm", "A flock of starlings."),
        ],
        sources=[
            ("Eusociality", "https://en.wikipedia.org/wiki/Eusociality"),
            ("Murmuration", "https://en.wikipedia.org/wiki/Murmuration"),
            ("Vervet monkey: alarm calls", "https://en.wikipedia.org/wiki/Vervet_monkey"),
        ],
    ),
    dict(
        id="camel-creation",
        title="Look at the Camels",
        hook="“Do they not look at the camels, how they were created?” Physiologists found a desert machine.",
        category="life",
        claimType="interpretive",
        verses=["88:17", "88:18"],
        words=[
            ("ٱلْإِبِلِ", "al-ibil", "the camels", "أ ب ل"),
            ("خُلِقَتْ", "khuliqat", "were created", "خ ل ق"),
            ("يَنظُرُونَ", "yanẓurūna", "they look", "ن ظ ر"),
        ],
        science=[
            "A camel's hump stores fat, not water, so it can go a long time without food. A thirsty camel can drink more than 100 litres in a few minutes, and can lose a quarter of its body weight in water and survive, which would kill most mammals.",
            "In 1840 George Gulliver noticed that camels' red blood cells are oval, not round: they can swell with water without bursting. In 1956 Knut Schmidt-Nielsen showed that a camel lets its body temperature swing between about 34 and 41 °C, saving water it would have sweated away.",
            "In 1993 researchers found that camels make antibodies built from a single chain, unlike ours. Fragments of them, called nanobodies, are now used in medicine; the first nanobody drug was approved in 2018.",
        ],
        stats=[
            ("34–41 °C", "Body-temperature swing that saves water (1956)"),
            ("1993", "Single-chain camel antibodies discovered"),
            (">100 L", "Water a thirsty camel can drink in minutes"),
        ],
        discovery=("1840 – 1993", "Gulliver · Schmidt-Nielsen · Hamers-Casterman"),
        media=[
            ("File:A camel ride in the Sahara Desert.jpg", "A camel in the Sahara."),
            ("File:Dromedary Arabian camel (8454264337).jpg", "A dromedary."),
            ("File:ASC Leiden - van de Bruinhorst Collection - Somaliland 2019 - 4658 - Close up of the heads of three dromedaries with long eyelashes, a a fourth dromedary with markings of its owner.jpg", "Long double eyelashes keep out the desert dust."),
        ],
        sources=[
            ("Camel", "https://en.wikipedia.org/wiki/Camel"),
            ("Nanobody", "https://en.wikipedia.org/wiki/Single-domain_antibody"),
            ("Knut Schmidt-Nielsen", "https://en.wikipedia.org/wiki/Knut_Schmidt-Nielsen"),
        ],
    ),
    dict(
        id="bird-flight",
        title="Birds Spread and Fold Their Wings",
        hook="Birds “spread their wings and fold them”, and “none holds them but the Most Merciful.”",
        category="life",
        claimType="interpretive",
        verses=["67:19", "16:79"],
        words=[
            ("صَٰٓفَّٰتٍ", "ṣāffātin", "spreading (their wings)", "ص ف ف"),
            ("وَيَقْبِضْنَ", "wa-yaqbiḍna", "and they fold them in", "ق ب ض"),
            ("جَوِّ", "jawwi", "the air, the atmosphere", "ج و و"),
            ("يُمْسِكُهُنَّ", "yumsikuhunna", "holds them", "م س ك"),
        ],
        science=[
            "A bird flies by pushing air down and back with its wings, which are shaped so that the air pressure beneath is higher than above, producing lift. Birds alternate between wings spread wide to glide and soar, and wings folded in to reduce drag or to flap.",
            "How lift works was understood only recently. Sir George Cayley separated lift from thrust around 1800. Otto Lilienthal made the first repeated glider flights in 1891. Martin Kutta and Nikolai Joukowski gave lift a mathematical theory around 1902–1906, and the Wright brothers flew a powered aeroplane in 1903.",
            "Nothing visible holds a bird up: the air itself supports its weight, as long as the wing keeps moving through it.",
        ],
        stats=[
            ("1891", "Lilienthal's first glider flights"),
            ("1903", "The Wright brothers' first powered flight"),
            ("≈1906", "Kutta–Joukowski theory of lift"),
        ],
        discovery=("c. 1800 – 1906", "Cayley · Lilienthal · Kutta & Joukowski"),
        media=[
            ("File:Bonellis eagle.jpg", "A Bonelli's eagle in flight, wings spread."),
            ("File:Copy of the glider O. Lilienthal in the Deutsches Museum. Munich, Germany.jpg", "A copy of one of Otto Lilienthal's gliders."),
            ("File:Eagle flight in the Arpi Natural-Historical Reserve.webm", "A large bird soaring."),
        ],
        sources=[
            ("Bird flight", "https://en.wikipedia.org/wiki/Bird_flight"),
            ("Lift (force)", "https://en.wikipedia.org/wiki/Lift_(force)"),
            ("Otto Lilienthal", "https://en.wikipedia.org/wiki/Otto_Lilienthal"),
        ],
    ),

    # ============================ HUMAN CREATION ============================
    dict(
        id="three-darknesses",
        title="Three Darknesses",
        hook="“He creates you in your mothers' wombs… within three darknesses”: the unborn child lies behind three layers.",
        category="human",
        claimType="interpretive",
        verses=["39:6"],
        words=[
            ("ظُلُمَٰتٍ", "ẓulumātin", "darknesses", "ظ ل م"),
            ("ثَلَٰثٍ", "thalāthin", "three", "ث ل ث"),
            ("بُطُونِ", "buṭūni", "the wombs, the bellies", "ب ط ن"),
            ("خَلْقًا", "khalqan", "creation, in stages", "خ ل ق"),
        ],
        science=[
            "The developing child lies behind three layers: the front wall of the mother's abdomen, the wall of the uterus, and the amniotic sac with its two membranes (the amnion and the chorion). The layers keep light out, cushion the child, and help guard against infection.",
            "Inside, the amniotic fluid, about 800 millilitres at its peak around weeks 34 to 36, protects the child from knocks and keeps its temperature steady.",
            "Classical commentators named three darknesses too, though a slightly different three: the belly, the womb and the membrane around the child.",
        ],
        stats=[
            ("3", "Layers: abdominal wall, uterine wall, amniotic membranes"),
            ("≈800 mL", "Amniotic fluid at its peak (weeks 34–36)"),
        ],
        discovery=("1774 – 1958", "William Hunter · Ian Donald (ultrasound)"),
        media=[
            ("File:Pregnancy by Jan van Riemsdyk and William Hunter.jpg", "An engraving from William Hunter's 1774 atlas of the pregnant uterus."),
            ("File:Hunter's Anatomy of the human gravid uterus. Wellcome M0012152.jpg", "A plate from Hunter's Anatomy of the Human Gravid Uterus (Wellcome Collection)."),
        ],
        sources=[
            ("Amniotic sac", "https://en.wikipedia.org/wiki/Amniotic_sac"),
            ("Fetal membranes", "https://en.wikipedia.org/wiki/Fetal_membranes"),
            ("William Hunter (anatomist)", "https://en.wikipedia.org/wiki/William_Hunter_(anatomist)"),
        ],
    ),
    dict(
        id="mixed-drop",
        title="A Mixed Drop",
        hook="Man was created from a “mixed drop”: two cells from two parents fuse into one.",
        category="human",
        claimType="interpretive",
        verses=["76:2", "86:5", "86:6", "86:7"],
        words=[
            ("نُّطْفَةٍ", "nuṭfatin", "a small drop", "ن ط ف"),
            ("أَمْشَاجٍ", "amshājin", "mixed, commingled", "م ش ج"),
            ("دَافِقٍ", "dāfiqin", "gushing, pouring forth", "د ف ق"),
            ("ٱلصُّلْبِ", "aṣ-ṣulbi", "the backbone, the loins", "ص ل ب"),
            ("وَٱلتَّرَآئِبِ", "wat-tarāʾibi", "and the ribs, the chest", "ت ر ب"),
        ],
        science=[
            "A human life begins when a sperm and an egg fuse into a single cell, the zygote, which holds 23 chromosomes from each parent. Karl Ernst von Baer found the mammalian egg in 1827, Oscar Hertwig watched fertilisation in 1875, and in 1956 Joe Hin Tjio and Albert Levan established that humans have 46 chromosomes.",
            "The organs that make sperm and eggs begin to form high in the abdomen, beside the kidneys and near the backbone, before they descend. They keep their blood supply and nerves from up there, which is why the testes' artery arises from the aorta close to the kidneys.",
        ],
        stats=[
            ("46", "Human chromosomes, 23 from each parent (1956)"),
            ("1875", "Fertilisation first watched (Hertwig)"),
        ],
        discovery=("1827 – 1956", "von Baer · Hertwig · Tjio & Levan"),
        media=[
            ("File:Egg cell fertilization - Zygote.png", "Fertilisation: sperm and egg fuse into a zygote."),
            ("File:Human Egg (2722043681).jpg", "A human egg cell surrounded by other cells."),
        ],
        sources=[
            ("Zygote", "https://en.wikipedia.org/wiki/Zygote"),
            ("Fertilisation", "https://en.wikipedia.org/wiki/Fertilisation"),
            ("Development of the gonads", "https://en.wikipedia.org/wiki/Gonad"),
        ],
    ),
    dict(
        id="breastfeeding-two-years",
        title="Two Years of Nursing, Six Months of Pregnancy",
        hook="Two full years of nursing, and a pregnancy of at least six months: numbers that health bodies and neonatologists use today.",
        category="human",
        claimType="parallel",
        verses=["2:233", "31:14", "46:15"],
        words=[
            ("حَوْلَيْنِ", "ḥawlayni", "two years", "ح و ل"),
            ("كَامِلَيْنِ", "kāmilayni", "two complete (years)", "ك م ل"),
            ("يُرْضِعْنَ", "yurḍiʿna", "they nurse", "ر ض ع"),
            ("وَفِصَٰلُهُۥ", "wa-fiṣāluhū", "and his weaning", "ف ص ل"),
            ("ثَلَٰثُونَ", "thalāthūna", "thirty (months)", "ث ل ث"),
        ],
        science=[
            "The World Health Organization and UNICEF recommend exclusive breastfeeding for the first six months and continued breastfeeding, alongside other foods, up to two years or beyond. A 2016 Lancet series estimated that universal breastfeeding could prevent about 823,000 child deaths a year.",
            "Verse 46:15 gives pregnancy and weaning together as thirty months. Subtract the two years of nursing (24 months) and six months are left: the reasoning of Ali ibn Abi Talib, approved by Uthman and the Companions, for the minimum length of a pregnancy.",
            "Modern intensive care has moved the limit of survival to about that point. In recent US hospital figures, about 25% of babies born at 22 weeks, 53% at 23 weeks and 71% at 24 weeks survive.",
        ],
        stats=[
            ("2 years", "WHO: continue breastfeeding to at least two years"),
            ("6 months", "Exclusive breastfeeding recommended"),
            ("71%", "Survival of babies born at 24 weeks in modern intensive care"),
        ],
        discovery=("2003 – 2016", "WHO/UNICEF guidance · Victora et al. (Lancet)"),
        media=[
            ("File:A Smallholder's Wife Breastfeeding Her Child.jpg", "A painting of a mother nursing her child."),
            ("File:Artificial incubator for premature babies, 1890 Wellcome M0012476.jpg", "An 1890 incubator for premature babies (Wellcome Collection)."),
        ],
        sources=[
            ("WHO: Infant and young child feeding", "https://www.who.int/news-room/fact-sheets/detail/infant-and-young-child-feeding"),
            ("Breastfeeding", "https://en.wikipedia.org/wiki/Breastfeeding"),
            ("Limit of viability", "https://en.wikipedia.org/wiki/Limit_of_viability"),
        ],
    ),
    dict(
        id="skin-pain",
        title="Skins Replaced to Feel the Pain",
        hook="“Every time their skins are burned, We replace them with other skins so they may taste the punishment”: pain is felt in the skin.",
        category="human",
        claimType="interpretive",
        verses=["4:56"],
        words=[
            ("جُلُودُهُم", "julūduhum", "their skins", "ج ل د"),
            ("نَضِجَتْ", "naḍijat", "were cooked through, burned", "ن ض ج"),
            ("بَدَّلْنَٰهُمْ", "baddalnāhum", "We exchange for them", "ب د ل"),
            ("لِيَذُوقُوا۟", "li-yadhūqū", "so that they may taste", "ذ و ق"),
        ],
        science=[
            "Pain is sensed by nociceptors, free nerve endings that fill the skin. In 1906 Charles Sherrington gave them their name, in 1969 Bessou and Perl recorded from single nociceptor fibres, and in 1997 David Julius's lab found the receptor that senses painful heat and the burn of chilli peppers (TRPV1). Julius and Ardem Patapoutian won the 2021 Nobel Prize for finding the receptors for heat, cold and touch.",
            "A very deep burn can destroy the nerve endings, so the burned spot itself may feel little, while the edges hurt terribly. Pain depends on living nerve endings in the skin, which is the point people draw from this verse.",
        ],
        stats=[
            ("1906", "“Nociceptor” named (Sherrington)"),
            ("1997", "Heat and pain receptor TRPV1 found (Julius)"),
            ("2021", "Nobel Prize for temperature and touch receptors"),
        ],
        discovery=("1906 – 2021", "Sherrington · Bessou & Perl · Julius & Patapoutian"),
        media=[
            ("File:Bird's Eye Chili.jpg", "Chilli peppers: capsaicin activates the same receptor that senses painful heat."),
            ("File:Anatomical relationship between keratinocytes and sensory nerve endings.jpg", "Sensory nerve endings among the cells of the skin."),
        ],
        sources=[
            ("Nociceptor", "https://en.wikipedia.org/wiki/Nociceptor"),
            ("TRPV1", "https://en.wikipedia.org/wiki/TRPV1"),
            ("The Nobel Prize 2021", "https://www.nobelprize.org/prizes/medicine/2021/press-release/"),
        ],
    ),
    dict(
        id="forelock-brain",
        title="The Lying, Sinful Forelock",
        hook="“We will seize him by the forelock, a lying, sinful forelock”: the front of the head, where deceit is planned.",
        category="human",
        claimType="interpretive",
        verses=["96:15", "96:16"],
        words=[
            ("بِٱلنَّاصِيَةِ", "bin-nāṣiyah", "by the forelock", "ن ص و"),
            ("كَٰذِبَةٍ", "kādhibatin", "lying", "ك ذ ب"),
            ("خَاطِئَةٍ", "khāṭiʾatin", "sinful, erring", "خ ط أ"),
        ],
        science=[
            "The part of the brain behind the forehead, the prefrontal cortex, plans, weighs choices and holds back impulses. In 1848 an iron rod passed through the front of Phineas Gage's brain; he survived, but his judgment and self-control changed so much that friends said he was no longer himself.",
            "Brain-scan studies since 2001 (Spence, Langleben and others) find that lying keeps the prefrontal cortex especially busy, because a lie means holding the truth in mind while building something false. Damage there, studied by Antonio Damasio and others, can wreck moral and social judgment.",
        ],
        stats=[
            ("1848", "Phineas Gage's frontal-lobe injury"),
            ("2001", "First brain-scan studies of lying"),
        ],
        discovery=("1848 – 2002", "Harlow · Damasio · Spence · Langleben"),
        media=[
            ("File:Prefrontal cortex.jpg", "The prefrontal cortex at the front of the brain."),
            ("File:Prefrontal cortex.png", "The prefrontal cortex highlighted on the brain."),
        ],
        sources=[
            ("Prefrontal cortex", "https://en.wikipedia.org/wiki/Prefrontal_cortex"),
            ("Phineas Gage", "https://en.wikipedia.org/wiki/Phineas_Gage"),
            ("Neuroscience of lying", "https://en.wikipedia.org/wiki/Deception#Neuroscience"),
        ],
    ),

    # ============================ THE COSMOS ============================
    dict(
        id="moon-phases",
        title="The Moon's Stages",
        hook="The Moon has stages, and the last is “like an old date-palm stalk”: the thin, curved crescent.",
        category="cosmos",
        claimType="parallel",
        verses=["36:39", "2:189"],
        words=[
            ("مَنَازِلَ", "manāzila", "stations, stages", "ن ز ل"),
            ("ٱلْعُرْجُونِ", "al-ʿurjūn", "the dry, curved date-stalk", "ع ر ج ن"),
            ("ٱلْقَدِيمِ", "al-qadīm", "the old", "ق د م"),
            ("ٱلْأَهِلَّةِ", "al-ahillah", "the crescents, the new moons", "ه ل ل"),
            ("مَوَٰقِيتُ", "mawāqītu", "measures of time", "و ق ت"),
        ],
        science=[
            "The Moon shines only by sunlight, and we see different amounts of its lit half as it circles the Earth. One full cycle of phases takes 29.53 days. In its last days the waning crescent is a thin, curved sliver, very like the dried, hooked stalk that a date cluster leaves on the palm.",
            "The cycle has been used to count time for thousands of years. Twelve lunar months make a year of about 354 days, so the lunar calendar drifts through the seasons: 33 lunar years are almost exactly 32 solar years.",
            "Galileo drew the Moon through a telescope in 1609–10, and Newton explained its orbit in 1687. In 1969 Apollo 11 left a reflector on the surface, and laser pulses have measured the Moon's distance to within centimetres ever since.",
        ],
        stats=[
            ("29.53 days", "One cycle of the Moon's phases"),
            ("≈354 days", "A lunar year of twelve months"),
            ("33 ≈ 32", "Lunar years that equal solar years"),
        ],
        media=[
            ("File:2011-11-19-Waning crescent moon.jpg", "A waning crescent Moon."),
            ("File:Galileo moon phases.jpg", "Galileo's drawings of the Moon in its phases."),
            ("File:Moon Phases 2021 - Northern Hemisphere - 4K.webm", "NASA: a full year of the Moon's phases."),
        ],
        sources=[
            ("Lunar phase", "https://en.wikipedia.org/wiki/Lunar_phase"),
            ("Lunar month", "https://en.wikipedia.org/wiki/Lunar_month"),
            ("Islamic calendar", "https://en.wikipedia.org/wiki/Islamic_calendar"),
        ],
    ),
    dict(
        id="night-strip-day",
        title="Night Is the Default",
        hook="“We strip the day from the night”: darkness is what space is like, and daylight is only a thin layer around the Earth.",
        category="cosmos",
        claimType="interpretive",
        verses=["36:37"],
        words=[
            ("نَسْلَخُ", "naslakhu", "We strip off (as a skin is peeled)", "س ل خ"),
            ("ٱلنَّهَارَ", "an-nahār", "the day", "ن ه ر"),
            ("مُّظْلِمُونَ", "muẓlimūna", "in darkness", "ظ ل م"),
        ],
        science=[
            "Away from the Earth, the sky is black even with the Sun shining: an astronaut sees a blazing Sun in a black sky. The bright blue daytime sky exists only because air scatters sunlight, an effect Lord Rayleigh explained in 1871.",
            "That layer of air is thin. The conventional edge of space, the Kármán line, is about 100 km up, on a planet 12,742 km across. In 1961 Yuri Gagarin reported that the sky above the air was very dark.",
            "So the picture of day as a thin layer stripped off a background of darkness is a fair description of what astronauts see.",
        ],
        stats=[
            ("1871", "Rayleigh explains the blue sky"),
            ("≈100 km", "Height of the layer of air that lights the sky by day"),
            ("1961", "Gagarin sees a very dark sky above the air"),
        ],
        discovery=("1871 – 1961", "Rayleigh · Gagarin"),
        media=[
            ("File:From Day Into Night.jpg", "Day turning to night at the Earth's edge, seen from the ISS (NASA)."),
            ("File:AS08-13-2329.jpg", "The Earth against a black sky, from Apollo 8."),
            ("File:Earth Views from the International Space Station.webm", "The Earth seen from the ISS."),
        ],
        sources=[
            ("Rayleigh scattering", "https://en.wikipedia.org/wiki/Rayleigh_scattering"),
            ("Kármán line", "https://en.wikipedia.org/wiki/K%C3%A1rm%C3%A1n_line"),
            ("Diffuse sky radiation", "https://en.wikipedia.org/wiki/Diffuse_sky_radiation"),
        ],
    ),
    dict(
        id="shooting-stars",
        title="Lamps and Missiles in the Sky",
        hook="The nearest heaven is adorned with “lamps” that are also “missiles”: shooting stars, grains of dust burning up.",
        category="cosmos",
        claimType="interpretive",
        verses=["67:5", "37:10"],
        words=[
            ("بِمَصَٰبِيحَ", "bi-maṣābīḥa", "with lamps", "ص ب ح"),
            ("رُجُومًا", "rujūman", "things cast, missiles", "ر ج م"),
            ("شِهَابٌ", "shihābun", "a flame, a blazing streak", "ش ه ب"),
            ("ثَاقِبٌ", "thāqibun", "piercing", "ث ق ب"),
        ],
        science=[
            "A shooting star is not a star. It is a grain of dust or rock, mostly the size of sand or a pea, hitting the atmosphere at 11 to 72 km/s and burning up in a bright streak.",
            "Ernst Chladni argued in 1794 that such things come from space. In 1833 Denison Olmsted showed that the Leonid meteors radiate from one point in the sky, so they must come from space. In 1866 Giovanni Schiaparelli showed that the Perseid meteors travel the orbit of Comet Swift–Tuttle: meteor showers are trails of comet dust that the Earth crosses each year.",
        ],
        stats=[
            ("11–72 km/s", "Speed of meteoroids entering the air"),
            ("1866", "Perseids traced to a comet (Schiaparelli)"),
        ],
        discovery=("1794 – 1866", "Chladni · Olmsted · Schiaparelli"),
        media=[
            ("File:2019-8-15 perseids meteor shower 1.jpg", "Perseid meteors streaking across the sky."),
            ("File:Meteor Ionisation Trail.jpg", "The glowing trail of a meteor."),
            ("File:03-Perseids 2021 Time-lapse-nX-1.webm", "Time-lapse of the Perseid meteor shower."),
        ],
        sources=[
            ("Meteoroid", "https://en.wikipedia.org/wiki/Meteoroid"),
            ("Perseids", "https://en.wikipedia.org/wiki/Perseids"),
            ("Meteor shower", "https://en.wikipedia.org/wiki/Meteor_shower"),
        ],
    ),

    # ============================ EARTH & SEAS ============================
    dict(
        id="mountains-moving",
        title="Mountains That Pass Like Clouds",
        hook="“You see the mountains, thinking them fixed, while they pass like clouds”: mountains really are moving.",
        category="earth",
        claimType="interpretive",
        verses=["27:88"],
        words=[
            ("جَامِدَةً", "jāmidatan", "fixed, solid", "ج م د"),
            ("تَمُرُّ", "tamurru", "they pass by", "م ر ر"),
            ("ٱلسَّحَابِ", "as-saḥābi", "the clouds", "س ح ب"),
        ],
        science=[
            "Mountains feel like the most fixed things there are, yet the ground is carried by the Earth's spin at up to 1,670 km/h at the equator without our feeling it. In 1851 Léon Foucault's pendulum made that turning visible.",
            "The mountains also travel on drifting plates. India is moving north into Asia at about 4–5 cm a year, pushing the Himalaya up by about half a centimetre a year. Alfred Wegener proposed continental drift in 1912, and the 1960s brought the theory of plate tectonics; GPS stations now measure the motion directly.",
        ],
        stats=[
            ("≈1,670 km/h", "Speed of the ground at the equator from the Earth's spin"),
            ("4–5 cm/yr", "Speed of India moving into Asia"),
            ("1851", "Foucault's pendulum shows the Earth turning"),
        ],
        discovery=("1851 – 1960s", "Foucault · Wegener · plate tectonics"),
        media=[
            ("File:Foucault Pendulum - Houston Museum of Natural Science 2019-06-25.jpg", "A Foucault pendulum, which shows the Earth turning."),
            ("File:IndiaMoving-revised 09-15.jpg", "India's northward journey into Asia (USGS)."),
            ("File:Foucault pendulum 1.webm", "A Foucault pendulum swinging."),
        ],
        sources=[
            ("Foucault pendulum", "https://en.wikipedia.org/wiki/Foucault_pendulum"),
            ("Plate tectonics", "https://en.wikipedia.org/wiki/Plate_tectonics"),
            ("Alfred Wegener", "https://en.wikipedia.org/wiki/Alfred_Wegener"),
        ],
    ),
    dict(
        id="sky-returns",
        title="The Sky That Returns",
        hook="“By the sky that returns”: the sky sends back rain, and even radio waves.",
        category="earth",
        claimType="interpretive",
        verses=["86:11", "86:12"],
        words=[
            ("ٱلرَّجْعِ", "ar-rajʿi", "the returning", "ر ج ع"),
            ("ٱلسَّمَآءِ", "as-samāʾi", "the sky", "س م و"),
            ("ٱلصَّدْعِ", "aṣ-ṣadʿi", "the splitting, the sprouting", "ص د ع"),
        ],
        science=[
            "The sky returns things to the Earth. Water evaporates, rises and comes back as rain, year after year. About 30% of the sunlight that reaches the Earth is sent straight back to space by clouds, air and surface.",
            "High in the atmosphere, a layer of charged gas, the ionosphere, bounces radio waves back down. In 1901 Guglielmo Marconi sent a radio signal across the Atlantic, over the curve of the Earth. In 1902 Oliver Heaviside and Arthur Kennelly proposed that a reflecting layer must exist, and Edward Appleton and Miles Barnett proved it in 1924 (Nobel Prize 1947).",
        ],
        stats=[
            ("≈30%", "Sunlight the Earth sends straight back to space"),
            ("1901", "First radio signal across the Atlantic"),
            ("1924", "Ionosphere proved by Appleton and Barnett"),
        ],
        discovery=("1901 – 1924", "Marconi · Heaviside & Kennelly · Appleton"),
        media=[
            ("File:Guglielmo Marconi.jpg", "Guglielmo Marconi."),
            ("File:Ionosphere-Thermosphere Processes.jpg", "The ionosphere and thermosphere high above the Earth (NASA)."),
        ],
        sources=[
            ("Ionosphere", "https://en.wikipedia.org/wiki/Ionosphere"),
            ("Skywave (radio)", "https://en.wikipedia.org/wiki/Skywave"),
            ("Albedo", "https://en.wikipedia.org/wiki/Albedo"),
        ],
    ),
]
