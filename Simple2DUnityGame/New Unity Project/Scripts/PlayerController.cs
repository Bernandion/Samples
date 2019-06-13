using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class PlayerController : MonoBehaviour {

    public float moveSpeed;
    public float shotSpeed;
    public float shotInterval;
    public int health;

    private float shotTimeLeft;

    private Rigidbody2D playerBody;

    private Slider bulspeed;
    private Dropdown playcol;

    public Rigidbody2D shoth;
    public Rigidbody2D shotv;


    // Use this for initialization
    void Start () {
        playerBody = GetComponent<Rigidbody2D>();
        shotTimeLeft = shotInterval;
        bulspeed = GameObject.Find("shotspeed").GetComponent<Slider>();
        playcol = GameObject.Find("colourselect").GetComponent<Dropdown>();
	}
	
	// Update is called once per frame
	void Update () {

        playerBody.velocity = new Vector2(Input.GetAxisRaw("Horizontal") * moveSpeed, Input.GetAxisRaw("Vertical") * moveSpeed);

        // set the shotspeed while main screen still active
        if (bulspeed.IsActive()) shotSpeed = bulspeed.value;
        // set the player colour while main screen still active
        if (playcol.IsActive()) {
            switch (playcol.value) {
                case 0: transform.localScale = new Vector3(1, 1, 1); break;
                case 1: transform.localScale = new Vector3(0.7f, 0.7f, 0.7f); break;
                case 2: transform.localScale = new Vector3(1.45f, 1.45f, 1.45f); break;
                default: break;
            }
        }
        
        // possibly shoot every allowed time interval
        shotTimeLeft -= Time.deltaTime;
        if (shotTimeLeft < 0) {
            Shoot();
            shotTimeLeft = shotInterval;
        }


    }

    void OnCollisionEnter2D(Collision2D collision) {
        if (collision.gameObject.tag == "enemy" || collision.gameObject.tag == "enemys" || collision.gameObject.tag == "enemyd" || collision.gameObject.tag == "eshot") {
            if (health > 1) {
                if (health == 3) Destroy(GameObject.Find("life3"));
                else if (health == 2) Destroy(GameObject.Find("life2"));
                health--;
                Destroy(collision.gameObject);
            }
            else {
                Destroy(GameObject.Find("life1"));
                if (collision.gameObject.tag == "eshot") {
                    Destroy(collision.gameObject);
                }
                
                Destroy(this.gameObject, 0.3f);
            }
        }
    }

    private void Shoot() {
        // if up is pressed, shoot up
        if (Input.GetKey("up")) {
            Rigidbody2D vertshot = Instantiate(shotv, transform.position, Quaternion.identity) as Rigidbody2D;
            vertshot.velocity = Vector2.up * shotSpeed;
            // ignore collisions between this object and the shot
            Physics2D.IgnoreCollision(vertshot.GetComponent<Collider2D>(), this.GetComponent<Collider2D>());
        }
        // if down is pressed, shoot down
        else if (Input.GetKey("down")) {
            Rigidbody2D vertshot = Instantiate(shotv, transform.position, Quaternion.identity) as Rigidbody2D;
            vertshot.velocity = Vector2.down * shotSpeed;
            // ignore collisions between this object and the shot
            Physics2D.IgnoreCollision(vertshot.GetComponent<Collider2D>(), this.GetComponent<Collider2D>());
        }
        // if right is pressed, shoot right
        else if (Input.GetKey("right")) {
            Rigidbody2D horishot = Instantiate(shoth, transform.position, Quaternion.identity) as Rigidbody2D;
            horishot.velocity = Vector2.right * shotSpeed;
            // ignore collisions between this object and the shot
            Physics2D.IgnoreCollision(horishot.GetComponent<Collider2D>(), this.GetComponent<Collider2D>());
        }
        // if left is pressed, shoot left
        else if (Input.GetKey("left")) {
            Rigidbody2D horishot = Instantiate(shoth, transform.position, Quaternion.identity) as Rigidbody2D;
            horishot.velocity = Vector2.left * shotSpeed;
            // ignore collisions between this object and the shot
            Physics2D.IgnoreCollision(horishot.GetComponent<Collider2D>(), this.GetComponent<Collider2D>());
        }
    }

}
