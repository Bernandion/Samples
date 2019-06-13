using System.Collections;
using System.Runtime;
using System.Collections.Generic;
using UnityEngine;
using System.Runtime.Serialization.Formatters.Binary;
using System.IO;
using UnityEngine.UI;
using UnityEngine.SceneManagement;
using System.Linq;

public class AreaController : MonoBehaviour {
    // static self reference object for load and save outside this class
    // (probably easier and more efficient to have 'playerDead' bool and just change it once
    // player is dead in player script, then save and open text here without needing to check Player gameobject)
    public static AreaController cont;

    public Rigidbody2D enemyl;
    public Rigidbody2D enemys;
    public Rigidbody2D enemyd;
    public float initialspawn;

    private float spawntime;
    private int roundspawnnum;
    private int spawncount;
    

    private int highscore;
    private List<Score> scrs;
    private string pname;

    private int round;
    private bool spawned;
    private Text roundtext, go1, go2, start, score, prevscores, prevscoresval;
    private GameObject inp;
    // bool for if player starts by pressing space
    private bool started;
    private bool easy = false;

	// Use this for initialization
	void Start () {
        // get text to manipulate
        roundtext = GameObject.Find("round").GetComponent<Text>();
        go1 = GameObject.Find("go1").GetComponent<Text>();
        go2 = GameObject.Find("go2").GetComponent<Text>();
        start = GameObject.Find("start").GetComponent<Text>();
        score = GameObject.Find("score").GetComponent<Text>();
        inp = GameObject.Find("infield");
        prevscores = GameObject.Find("prevscores").GetComponent<Text>();
        prevscoresval = GameObject.Find("prevscoresval").GetComponent<Text>();
        go1.enabled = false;
        go2.enabled = false;
        score.enabled = false;
        inp.SetActive(false);
        prevscores.enabled = false;
        prevscoresval.enabled = false;
        start.enabled = true;
        spawntime = initialspawn;
        // number of times spawning is done per round
        roundspawnnum = 5;
        spawncount = 0;
        round = 1;
        spawned = false;
        started = false;
        // instantiate static self reference for outside use
        cont = this;
        // load the highscore and scores on initalization of the scene
        Load();
	}
	
	// Update is called once per frame
	void Update () {
        // game started
        if (started) {
            // if player is dead, display the restart text (inefficient, should use bool for Player)
            if (GameObject.FindWithTag("Player") == null) {
                // show score page to enter name
                go1.enabled = true;
                go2.enabled = true;
                score.enabled = true;
                inp.SetActive(true);
                score.text = "Score: " + round;
                prevscores.enabled = true;
                prevscoresval.enabled = true;
                prevscores.text = "SCORES";
                prevscoresval.text = "";
                if (scrs != null) {
                    for (int i = 0; i < scrs.Count; i++) {
                        prevscores.text += "\n" + scrs[i].name;
                        prevscoresval.text += scrs[i].val + "\n";
                    }
                }
                if (Input.GetKey("return") || Input.GetKey("enter") && GameObject.Find("input").GetComponent<Text>().text.Length <= 16) {
                    pname = GameObject.Find("input").GetComponent<Text>().text;
                    // save the current session and reload main page
                    Save();
                    SceneManager.LoadScene("main");
                }
            }
            // if just starting, show first round
            if (round == 1 && spawncount == 0) {
                StartCoroutine(ShowRoundNum());
            }
            // counts down until time to spawn another enemy
            spawntime -= Time.deltaTime;
            // spawns the number of enemies by the current round
            if (spawntime < 0 && spawncount < roundspawnnum && GameObject.FindWithTag("Player") != null) {
                // every 3 rounds, spawn 1 shooter enemy at the start of the round as the first enemy
                if (spawncount == 0 && round % 3 == 0) {
                    SpawnShooter();
                    // spawn 2 shooters every 6 rounds
                    if (round % 6 == 0) {
                        SpawnShooter();
                    }
                }
                // every 5 rounds, spawn groups of enemies every 2 spawns
                else if (spawncount % 2 == 0 && round % 10 == 0) {
                    // spawns group of enemies depending on round number
                    for (int i = 0; i < round/10 + 1; i++) {
                        SpawnEnemy();
                    }
                }
                // else spawn enemies normally
                else {
                    // spawns the number of enemies by the current round
                    SpawnEnemy();
                }
                spawntime = initialspawn;
                spawncount++;
                // spawning done
                if (spawncount == roundspawnnum - 1) {
                    spawned = true;
                }
            }
            // if all enemies dead and have been spawned, can go to next round
            if ((GameObject.FindWithTag("enemy") == null && GameObject.FindWithTag("enemys") == null && GameObject.FindWithTag("enemyd") == null) && spawned) {
                round++;
                StartCoroutine(ShowRoundNum());
                // enemies spawn a little faster up to limit
                if (initialspawn > 0.5f) initialspawn -= 0.1f;
                // more enemies spawn
                if (easy) {
                    roundspawnnum++;
                }
                else {
                    roundspawnnum += 2;
                }
                // reset spawncount
                spawncount = 0;
                // reset spawned
                spawned = false;
            }
        }
        else if (!started) {
            // show intro screen
            if (Input.GetKey("s")) {
                // start game
                started = true;
                GameObject.Find("startcanv").SetActive(false);
            }
        }

    }

    // public method for toggle action
    public void OnToggle() {
        easy = GameObject.Find("easy").GetComponent<Toggle>().isOn;
    }

    // shows the round for 2 seconds
    private IEnumerator ShowRoundNum() {
        roundtext.text = "ROUND " + round;
        roundtext.enabled = true;
        yield return new WaitForSeconds(2);
        if (round > highscore) {
            GameObject.Find("highscore").GetComponent<Text>().text = "Highest Round: " + round;
        }
        roundtext.enabled = false;
    }

    // spawn enemy in random location around circle
    private void SpawnEnemy() {
        // get the x and y coordinates for spawning enemies randomly outside camera view
        // spawns in a random location in a circle
        float xval = Random.Range(-15.00f, 15.00f);
        float yval = 0f;
        if (xval >= 0) {
            yval = 15 - xval;
            // 50% change to be on either the top or bottom half of right side of circle
            if (Random.Range(0, 2) == 0) {
                yval = yval * -1f;
            }
        }
        else if (xval < 0) {
            yval = -15 - xval;
            // 50% change to be on either the top or bottom half of left side of circle
            if (Random.Range(0, 2) == 0) {
                yval = yval * -1f;
            }
        }
        // chance to spawn either large or small enemy
        // 20% chance for larger enemy
        if (Random.Range(0, 10) > 7) {
            Instantiate(enemyl, new Vector3(xval, yval), Quaternion.identity);
        }
        else {
            Rigidbody2D se = Instantiate(enemys, new Vector3(xval, yval), Quaternion.identity) as Rigidbody2D;
            // set the new enemy transform.right to face the Player's location
            se.GetComponent<Transform>().right = GameObject.FindGameObjectWithTag("Player").GetComponent<Transform>().position - se.GetComponent<Transform>().position;
            // give the small enemy velocity in the direction of the player
            se.velocity = se.GetComponent<Transform>().right * se.GetComponent<EnemyController>().moveSpeed;
        }
    }

    // spawns a shooting enemy at one of 4 locations in the area
    // moves spawned shooter in vertical motion
    private void SpawnShooter() {
        int rand = Random.Range(0, 4);
        // upper left spawn
        if (rand == 0) {
            Rigidbody2D en = Instantiate(enemyd, new Vector3(-7.5f, 7.0f), Quaternion.identity) as Rigidbody2D;
            en.velocity = Vector3.down * en.gameObject.GetComponent<EnemyController>().moveSpeed;
        }
        // upper right spawn
        else if (rand == 1) {
            Rigidbody2D en = Instantiate(enemyd, new Vector3(7.5f, 7.0f), Quaternion.identity) as Rigidbody2D;
            en.velocity = Vector3.down * en.gameObject.GetComponent<EnemyController>().moveSpeed;
        }
        // lower left spawn
        else if (rand == 2) {
            Rigidbody2D en = Instantiate(enemyd, new Vector3(-7.5f, -7.0f), Quaternion.identity) as Rigidbody2D;
            en.velocity = Vector3.up * en.gameObject.GetComponent<EnemyController>().moveSpeed;
        }
        // lower right spawn
        else if (rand == 3) {
            Rigidbody2D en = Instantiate(enemyd, new Vector3(7.5f, -7.0f), Quaternion.identity) as Rigidbody2D;
            en.velocity = Vector3.up * en.gameObject.GetComponent<EnemyController>().moveSpeed;
        }
    }

    /*void OnTriggerEnter2D(Collider2D collision) {
        // if the shooter has already been through the trigger, reverse its velocity in the y coordinate
        if (collision.gameObject.tag == "enemyd") {
            if (collision.gameObject.GetComponent<EnemyController>().through) {
                Rigidbody2D cur = collision.GetComponent<Rigidbody2D>();
                Vector2 curvel = cur.velocity;
                cur.velocity = new Vector2(curvel.x, curvel.y * -1);
            }
        }

    }

    void OnTriggerExit2D(Collider2D collision) {
        if (collision.gameObject.tag == "enemyd" || collision.gameObject.tag == "enemys") {
            // set through to true for the enemy
            collision.gameObject.GetComponent<EnemyController>().through = true;
        }
    }*/

    // private inner struct to represent scores by name and value
    [System.Serializable]
    private struct Score {
        public string name;
        public int val;
    }

    // saves highscore to save file
    public void Save() {
        // set the highscore for the open application
        if (round > highscore) highscore = round;
        // show the current highscore
        GameObject.Find("highscore").GetComponent<Text>().text = "Highest Round: " + highscore;
        // serialize the GameData class
        BinaryFormatter bf = new BinaryFormatter();
        FileStream file = File.Create(Application.persistentDataPath + "/scoreinfo.dat");
        GameData gd;
        // if first time running, dont need to update GameData scores
        if (scrs == null) gd = new GameData();
        else gd = new GameData(scrs);
        gd.SetHighscore(highscore);
        gd.SetScore(round, pname);
        // refresh the scrs List for the current running session
        this.scrs = gd.GetScores();

        bf.Serialize(file, gd);
        file.Close();
    }

    // loads the highscore from save file
    public void Load() {
        if (File.Exists(Application.persistentDataPath + "/scoreinfo.dat")) {
            // deserialize the GameData class
            BinaryFormatter bf = new BinaryFormatter();
            FileStream file = File.Open(Application.persistentDataPath + "/scoreinfo.dat", FileMode.Open);
            GameData gd = bf.Deserialize(file) as GameData;
            file.Close();

            highscore = gd.GetHighscore();
            scrs = gd.GetScores();
            // show the current highscore
            GameObject.Find("highscore").GetComponent<Text>().text = "Highest Round: " + highscore;
        }
    }

    // class to serialize to save data
    [System.Serializable]  // similar to implimenting the interface in Java for Serializable
    private class GameData {
        // highscore of previous round
        private int highscore;

        // List of all scores
        private List<Score> scores;

        public GameData() {
            this.scores = new List<Score>();
        }

        public GameData(List<Score> scs) {
            this.scores = scs;
        }

        public void SetHighscore(int score) {
            this.highscore = score;
        }

        public int GetHighscore() {
            return this.highscore;
        }

        // update the scores list
        public void SetScore(int score, string pname) {
            if (scores.Count >= 15 && score > scores[14].val) {
                scores.RemoveAt(14);
                Score scr = new Score();
                scr.name = pname;
                scr.val = score;
                scores.Add(scr);
                // order in descending
                scores = scores.OrderByDescending(o => o.val).ToList();
            }
            else if (scores.Count < 15) {
                Score scr = new Score();
                scr.name = pname;
                scr.val = score;
                scores.Add(scr);
                // order in descending
                scores = scores.OrderByDescending(o => o.val).ToList();
            }
        }

        public List<Score> GetScores() {
            return this.scores;
        }
    }

}
