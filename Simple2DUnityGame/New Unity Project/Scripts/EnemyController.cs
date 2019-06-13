using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EnemyController : MonoBehaviour {

    public int health;
    public float moveSpeed;
    public float shotSpeed;
    public float shotInterval;
    public Rigidbody2D eshot;
    public bool canshoot;
    

    private float shotTimeLeft;
    public bool through;

    private Rigidbody2D enemybody;

	// Use this for initialization
	void Start () {
        enemybody = GetComponent<Rigidbody2D>();
        shotTimeLeft = shotInterval;
        through = false;
	}
	
	// Update is called once per frame
	void Update () {
        // moves the enemy in the direction of the player
        // rotates enemy to face the direction of the player (transform.right for 2d rotation)
        if (GameObject.FindWithTag("Player") != null) {
            transform.right = GameObject.FindGameObjectWithTag("Player").GetComponent<Transform>().position - transform.position;
        }
        if (!canshoot && this.tag != "enemys") {
            enemybody.velocity = transform.right * moveSpeed;
        }

        // possibly shoot every allowed time interval
        if (canshoot && through) {
            shotTimeLeft -= Time.deltaTime;
        }
        if (shotTimeLeft < 0 && canshoot && through) {
            Shoot();
            shotTimeLeft = shotInterval;
        }
    }

    void OnCollisionEnter2D(Collision2D collision) {
        if (collision.gameObject.tag == "shot") {
            if (health > 1) {
                health--;
                Destroy(collision.gameObject);
            }
            else {
                Destroy(collision.gameObject);
                Destroy(this.gameObject);
            }
        }
    }

    // shoots in a cone of 3 shots towards the players location
    private void Shoot() {
        // shoot 3 shots in the general direction of the player
        for (int i=0; i<3; i++) {
            Rigidbody2D eneshot = Instantiate(eshot, transform.position, Quaternion.identity) as Rigidbody2D;
            Transform enetran = eneshot.GetComponent<Transform>();
            enetran.right = this.transform.right;
            switch (i) {
                case 0: enetran.Rotate(0f, 0f, -20f); break;
                case 1: break;
                case 2: enetran.Rotate(0f, 0f, 20f); break;
                default: Debug.Log("Enemy Shoot Error"); break;
            }
            eneshot.velocity = enetran.right * shotSpeed;
            // ignore collisions between this object and the shot
            Physics2D.IgnoreCollision(eneshot.GetComponent<Collider2D>(), this.GetComponent<Collider2D>());
        }
    }

}
