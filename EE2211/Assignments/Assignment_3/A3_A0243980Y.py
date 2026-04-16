import math
import numpy as np

# ─────────────────────────────────────────────────────────────────────────────
# HELPER FUNCTIONS TO IMPLEMENT
# ─────────────────────────────────────────────────────────────────────────────
# The following two helper functions will be used in Tasks A, B and C.
# Implement them correctly once, and they'll work for all tasks!

def _gd_1d(grad_fn, x0, lr, num_iters):
    """TODO: Complete this function to perform 1D gradient descent.
    
    Returns array of x after each step (length num_iters).
    """
    x = float(x0)
    traj = np.empty(num_iters)
    
    for i in range(num_iters):
        # TODO: Write the gradient descent update
        # x = x - lr * ??
        # Store the updated x in traj[i] AFTER the update
        x = x - lr * grad_fn(x)
        traj[i] = x
    
    return traj


def _gd_2d(grad_fn, u0, v0, lr, num_iters):
    """TODO: Complete this function to perform 2D gradient descent.
    
    Returns (u_traj, v_traj) as two numpy arrays, each length num_iters.
    """
    u, v = float(u0), float(v0)
    ut = np.empty(num_iters)
    vt = np.empty(num_iters)
    
    for i in range(num_iters):
        # TODO: gu, gv = grad_fn(u, v)
        # TODO: Update u and v using the gradients
        #       u = u - lr * gu
        #       v = v - lr * gv
        # TODO: Store updated values in ut[i] and vt[i]
        gu, gv = grad_fn(u, v)
        u = u - lr * gu
        v = v - lr * gv
        ut[i] = u
        vt[i] = v

    return ut, vt

# Quick test for you to verify your 1D helper functions. 
# if __name__ == "__main__":
#     # Test 1D with simple function f(x)=x^2, df(x)=2x
#     def test_df(x):
#         return 2 * x
#     test_traj = _gd_1d(test_df, 10.0, 0.1, 5)
#     print("1D test:", test_traj)  # Should see decreasing values

# You can design another test to verify your 2D helper function as well. 

# ─────────────────────────────────────────────────────────────────────────────
# Please replace "StudentMatriculationNumber" with your actual matric number
# in BOTH the filename and the function name below.
# e.g. if your matric number is A1234567R:
#   filename : A3_A1234567R.py
#   function : def A3_A1234567R(task, params)
# ─────────────────────────────────────────────────────────────────────────────
def A3_A0243980Y(task: str, params: dict) -> dict:
    """
    Input
    -----
    task   : str   one of "A", "B", "C"
    params : dict  task-specific inputs (see below)

    Returns
    -------
    dict of task-specific outputs (keys must match exactly)

    ── Task A ──────────────────────────────────────────────────────────────
    params keys:
        lr_list   (list of floats)  learning rates to test
        num_iters (int)             number of GD iterations per lr

    output keys:
        trajectories  dict  lr -> numpy array length num_iters (x after each step)
        final_f       dict  lr -> float (f at final x)
        diverging_lrs list  sorted list of lr values where |x_final| > 1e6
        converged_lrs list  sorted list of lr values where f_final < 5.1
        f_final_slow  float f_final for lr=0.001, rounded to 2 dp

    Hint:  After the loop, trajectories might look like:
    trajectories = {
    0.001: array([9.8, 9.6, 9.4, ...]),  # numpy array for lr=0.001
    0.01:  array([9.0, 8.1, 7.3, ...]),  # numpy array for lr=0.01
    0.1:   array([5.0, 2.5, 1.2, ...])    # numpy array for lr=0.1
    }    

    ── Task B ──────────────────────────────────────────────────────────────
    params keys:
        init_list (list of floats)  initial x values
        lr        (float)           learning rate
        num_iters (int)

    output keys:
        final_x          dict  init -> float (final x)
        final_f          dict  init -> float (f at final x)
        trajectories     dict  init -> numpy array length num_iters
        left_basin_inits list  sorted inits where x_final < 0
        global_min_side  str   "left" or "right"
        best_f           float lowest f_final across all inits, rounded to 2 dp

    ── Task C ──────────────────────────────────────────────────────────────
    params keys:
        init_pairs (list of [u0, v0])  starting points
        lr         (float)
        num_iters  (int)

    output keys:
        final_uv             dict  str(init_pair) -> [final_u, final_v]
        final_f              dict  str(init_pair) -> float
        num_basins           int   number of distinct basins (group by round(f_final, 1))
        same_basin_as_first  list  sorted str keys (excl. "[0.1, 0.1]") in same basin as "[0.1, 0.1]"
        best_init            str   key with lowest final_f

    """

    if task == "A":
        return _task_A(params)
    elif task == "B":
        return _task_B(params)
    elif task == "C":
        return _task_C(params)
    else:
        raise ValueError(f"Unknown task '{task}'. Must be one of A, B, C.")


# ─────────────────────────────────────────────────────────────────────────────
# TASK A
# Cost function : f(x) = (x - 3)^2 + 5
# Initialisation: x0 = 10.0
# ─────────────────────────────────────────────────────────────────────────────

def _task_A(params):
    lr_list   = params["lr_list"]
    num_iters = params["num_iters"]

    X0 = 10.0  # fixed initialisation — do not change

    # --- cost function and gradient ---
    def f(x):
        return (x - 3)**2 + 5

    def df(x):
        return 2 * (x - 3)

    trajectories = {}
    final_f      = {}

    for lr in lr_list:
        traj = _gd_1d(df, X0, lr, num_iters)
        trajectories[lr] = traj
        final_f[lr] = float(f(traj[-1]))

    # --- analysis outputs ---
    # TODO: fill in the three analysis keys

    diverging_lrs = []   # sorted list of lrs where |x_final| > 1e6
    converged_lrs = []   # sorted list of lrs where f_final < 5.1

    for lr, traj in trajectories.items():
        x_final = traj[-1]
        f_final = final_f[lr]

        if abs(x_final) > 1e6:
            diverging_lrs.append(lr)
        elif f_final < 5.1:
            converged_lrs.append(lr)
    diverging_lrs.sort()
    converged_lrs.sort()
    
    # Hint: find the learning rate equal to 0.001 and compute round(final_f[lr], 2)
    f_final_slow  = 0.0  # f_final for lr = 0.001, rounded to 2 dp
    f_final_slow = round(final_f[0.001], 2)

    return {
        "trajectories":  trajectories,
        "final_f":       final_f,
        "diverging_lrs": diverging_lrs,
        "converged_lrs": converged_lrs,
        "f_final_slow":  f_final_slow,
    }


# ─────────────────────────────────────────────────────────────────────────────
# TASK B
# Cost function : f(x) = x^4 - 8x^2 + x + 10
# ─────────────────────────────────────────────────────────────────────────────

def _task_B(params):
    init_list = params["init_list"]
    lr        = params["lr"]
    num_iters = params["num_iters"]

    # --- cost function and gradient ---
    def f(x):
        return x**4 - 8*x**2 + x + 10

    def df(x):
        return 4*x**3 - 16*x + 1

    final_x      = {}
    final_f      = {}
    trajectories = {}

    for x0 in init_list:
        traj = _gd_1d(df, x0, lr, num_iters)
        trajectories[x0] = traj
        final_x[x0]      = float(traj[-1])
        final_f[x0]      = float(f(traj[-1]))

    # --- analysis outputs ---
    # TODO: fill in the three analysis keys

    left_basin_inits = []     # sorted inits where x_final < 0
    global_min_side  = ""     # "left" or "right"
    best_f           = 0.0    # lowest f_final, rounded to 2 dp

    for x0, x_final in final_x.items():
        if x_final < 0:
            left_basin_inits.append(x0)
    left_basin_inits.sort()

    left_finals = [final_f[x0] for x0 in init_list if final_x[x0] < 0]
    right_finals = [final_f[x0] for x0 in init_list if final_x[x0] >= 0]

    #calculate mean for each side
    left_mean = sum(left_finals) / len(left_finals)
    right_mean = sum(right_finals) / len(right_finals)
    global_min_side = "left" if left_mean < right_mean else "right"

    best_f = round(min(final_f.values()), 2)

    return {
        "final_x":          final_x,
        "final_f":          final_f,
        "trajectories":     trajectories,
        "left_basin_inits": left_basin_inits,
        "global_min_side":  global_min_side,
        "best_f":           best_f,
    }


# ─────────────────────────────────────────────────────────────────────────────
# TASK C
# Cost function : f(u, v) = sin(u)*cos(v) + 0.1*(u^2 + v^2)
# ─────────────────────────────────────────────────────────────────────────────

def _task_C(params):
    init_pairs = params["init_pairs"]
    lr         = params["lr"]
    num_iters  = params["num_iters"]

    # --- cost function and gradients ---
    def f(u, v):
        return math.sin(u) * math.cos(v) + 0.1 * (u**2 + v**2)

    def df_du(u, v):
        return math.cos(u) * math.cos(v) + 0.2 * u

    def df_dv(u, v):
        return -math.sin(u) * math.sin(v) + 0.2 * v

    final_uv = {}
    final_f  = {}
    u_traj   = {} #no need to return, for debugging only
    v_traj   = {} #no need to return, for debugging only

    def grad_fn(u, v):
        # This function will work correctly once df_du and df_dv are implemented above
        return (df_du(u, v), df_dv(u, v))

    for pair in init_pairs:
        u0, v0 = pair[0], pair[1]
        key    = str(pair)   # e.g. "[0.1, 0.1]"  — use this as the dict key

        ut, vt = _gd_2d(grad_fn, u0, v0, lr, num_iters)

        u_traj[key]   = ut
        v_traj[key]   = vt
        final_uv[key] = [float(ut[-1]), float(vt[-1])]
        final_f[key]  = float(f(ut[-1], vt[-1]))

    # --- HINT for analysis outputs ---
    # To determine basins, round each final_f value to 1 decimal place.
    # For example, if final_f for a key is -0.79, rounded_f = -0.8.
    # Two runs are in the same basin if their rounded_f values are equal.

    # --- analysis outputs ---
    # TODO: fill in the three analysis keys
    # HINT
    # 1. You can create a dictionary 'rounded_f' mapping each key to round(final_f[key], 1)
    # 2. num_basins = len(set(rounded_f.values()))
    # 3. Let key0 = str([0.1, 0.1])
    #    For same_basin_as_first: find the key (excluding key0)
    #    where rounded_f[key] == rounded_f[key0]
    # 4. best_init = min(final_f, key=final_f.get)

    num_basins       = 0   # number of distinct basins (group by round(f_final, 1))
    rounded_f        = {key: round(f, 1) for key, f in final_f.items()}
    num_basins       = len(set(rounded_f.values()))


    same_basin_as_first = []  # sorted str keys (excl. "[0.1, 0.1]") in same basin as [0.1, 0.1]
    key0 = str([0.1, 0.1])
    for key, rf in rounded_f.items():
        if key != key0 and rf == rounded_f[key0]:
            same_basin_as_first.append(key)
    same_basin_as_first.sort()

    best_init        = ""  # str key with the lowest final_f
    best_init = min(final_f, key=final_f.get)

    return {
        "final_uv":        final_uv,
        "final_f":         final_f,
        "num_basins":      num_basins,
        "same_basin_as_first": same_basin_as_first,
        "best_init":       best_init,
    }