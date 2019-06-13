using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BouncerController : MonoBehaviour {

	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
		
	}

    void OnTriggerEnter2D(Collider2D collision) {
        // let small enemies bounce off depending on the bouncer they hit, changes velocity in opposite direction
        if (collision.gameObject.tag == "enemys") {
            if (collision.gameObject.GetComponent<EnemyController>().through) {
                if (this.name == "btop" || this.name == "bbot") {
                    Rigidbody2D cur = collision.GetComponent<Rigidbody2D>();
                    Vector2 curvel = cur.velocity;
                    cur.velocity = new Vector2(curvel.x, curvel.y * -1);
                }
                else if (this.name == "bleft" || this.name == "bright") {
                    Rigidbody2D cur = collision.GetComponent<Rigidbody2D>();
                    Vector2 curvel = cur.velocity;
                    cur.velocity = new Vector2(curvel.x * -1, curvel.y);
                }
            }
        }
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
    }
}
