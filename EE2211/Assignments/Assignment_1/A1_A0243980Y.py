import numpy as np


# Please replace "MatricNumber" with your actual matric number here and in the filename
# Please do not round values during intermediate steps (if any)
def A1_A0243980Y(X, y):
    """
    Input type
    :X type: numpy.ndarray
    :y type: numpy.ndarray

    Return type
    :InvXTX type: numpy.ndarray
    :w type: numpy.ndarray
    :y_pred: numpy.ndarray
   
    """

    # your code goes here

    XTX = (X.T)@X
    InvXTX = np.linalg.inv(XTX)
    w = InvXTX @ X.T @ y
    y_pred = X @ w

    # return in this order
    return InvXTX, w, y_pred